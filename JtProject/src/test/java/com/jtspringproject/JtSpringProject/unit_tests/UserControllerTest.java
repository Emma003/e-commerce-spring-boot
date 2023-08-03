package com.jtspringproject.JtSpringProject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @Mock
    private Model model;

    @Mock
    private Connection mockConnection;

    @Mock
    private Statement mockStatement;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private UserController userController;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userController = new UserController();
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        // Mock the behavior of the Transport.send method
        doAnswer((Answer<Void>) invocation -> {
            Message message = invocation.getArgument(0);
            System.out.println("Mock Email sent successfully to " + message.getAllRecipients()[0].toString());
            return null;
        }).when(mockTransport).send(any(Message.class));
    }

    @Test
    public void testRegisterUser() {
        // Act
        String viewName = userController.registerUser();

        // Assert
        assertEquals("register", viewName);
    }

    @Test
    public void testContact() {
        // Act
        String viewName = userController.contact();

        // Assert
        assertEquals("contact", viewName);
    }

    @Test
    public void testShop() throws Exception {
        // Arrange
        ArrayList<ShopItem> expectedShopItems = new ArrayList<>();
        expectedShopItems.add(new ShopItem("image1.jpg", "Product 1", 50, 1, "Suggested Product 1"));
        expectedShopItems.add(new ShopItem("image2.jpg", "Product 2", 60, 2, "Suggested Product 2"));

        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("image")).thenReturn("image1.jpg", "image2.jpg");
        when(mockResultSet.getString("name")).thenReturn("Product 1", "Product 2");
        when(mockResultSet.getInt("price")).thenReturn(50, 60);
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getInt("suggestedItem")).thenReturn(3, 4);

        ResultSet mockSuggestedItemResultSet = mock(ResultSet.class);
        when(mockStatement.executeQuery("select name from products where id = 3;")).thenReturn(mockSuggestedItemResultSet);
        when(mockSuggestedItemResultSet.next()).thenReturn(true, false);
        when(mockSuggestedItemResultSet.getString("name")).thenReturn("Suggested Product 1");

        ResultSet mockSuggestedItemResultSet2 = mock(ResultSet.class);
        when(mockStatement.executeQuery("select name from products where id = 4;")).thenReturn(mockSuggestedItemResultSet2);
        when(mockSuggestedItemResultSet2.next()).thenReturn(true, false);
        when(mockSuggestedItemResultSet2.getString("name")).thenReturn("Suggested Product 2");

        // Act
        String resultView = userController.shop(model);

        // Assert
        assertEquals("shop", resultView);

        @SuppressWarnings("unchecked")
        ArrayList<ShopItem> actualShopItems = (ArrayList<ShopItem>) model.asMap().get("shopItems");
        assertEquals(expectedShopItems.size(), actualShopItems.size());
        assertEquals(expectedShopItems.get(0).getProductName(), actualShopItems.get(0).getProductName());
        assertEquals(expectedShopItems.get(1).getProductName(), actualShopItems.get(1).getProductName());

        verify(model).addAttribute("shopItems", actualShopItems);
    }

    @Test
    public void testSubmitContact() {
        // Arrange
        String name = "John Doe";
        String email = "johndoe@example.com";
        String subject = "Test Subject";
        String message = "Test Message";
        boolean subscribe = true;
        String inquiryType = "General Inquiry";

        // Act
        String resultView = userController.submitContact(name, email, subject, message, subscribe, inquiryType, model, redirectAttributes);

        // Assert
        assertEquals("redirect:/contact", resultView);

        // Verify that the sendEmail method was called twice with the expected arguments
        verify(emailService, times(1)).sendEmail(eq(email), eq("bestfood438@gmail.com"), eq("Your Contact Request"), anyString());
        verify(emailService, times(1)).sendEmail(eq("bestfood438@gmail.com"), eq(email), eq("New Contact Request"), anyString());

        // Verify that the successMessage attribute was added to the RedirectAttributes
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("successMessage"), anyString());
    }

    @Test
    public void testSendEmail() {
        // Arrange
        String recipient = "recipient@example.com";
        String sender = "sender@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        // Mock the behavior of the Session.getInstance method
        when(mockSession.getInstance(any(Properties.class), any(Authenticator.class))).thenReturn(mockSession);

        // Mock the behavior of the Session.getTransport method
        when(mockSession.getTransport("smtp")).thenReturn(mockTransport);

        // Act
        userController.sendEmail(recipient, sender, subject, body);

        // Assert
        verify(mockSession).getInstance(any(Properties.class), any(Authenticator.class));
        verify(mockSession).getTransport("smtp");
        verify(mockTransport).connect("smtp.gmail.com", "bestfood438@gmail.com", "niwmagtnrxcdcehq");
        verify(mockTransport).sendMessage(any(MimeMessage.class), eq(mockTransport.getRecipients(RecipientType.TO)));
        verify(mockTransport).close();
    }



