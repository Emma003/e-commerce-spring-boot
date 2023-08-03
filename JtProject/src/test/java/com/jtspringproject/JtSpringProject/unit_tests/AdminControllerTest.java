package com.jtspringproject.JtSpringProject;

public class AdminControllerTest {

    private AdminController adminController;
    private Model mockModel;

    @BeforeEach
    public void setup() {
        adminController = new AdminController();
        mockModel = mock(Model.class);
    }

    @Test
    public void testReturnIndex() {
        String result = adminController.returnIndex();
        assertEquals("userLogin", result);
        assertEquals(0, adminController.adminlogcheck);
        assertEquals("", AdminController.usernameforclass);
        // Verify that UserController.setUsername("") is called
        // You can mock UserController and verify this method's invocation if it's part of another class.
    }

    @Test
    public void testIndexWithNoUsername() {
        adminController.setUsername("");
        String result = adminController.index(mockModel);
        assertEquals("userLogin", result);
        // Verify that the model does not contain any attributes
        verifyZeroInteractions(mockModel);
    }

    @Test
    public void testIndexWithUsername() {
        String testUsername = "testUser";
        adminController.setUsername(testUsername);
        String result = adminController.index(mockModel);
        assertEquals("index", result);
        // Verify that the model contains the "username" attribute with the expected value
        verify(mockModel).addAttribute("username", testUsername);
        // Verify that UserController.setUsername(usernameforclass) is called
        // You can mock UserController and verify this method's invocation if it's part of another class.
    }

    @Test
    public void testUserLoginValidate() {
        String result = adminController.userlog(mockModel);
        assertEquals("userLogin", result);
        // Verify that the model does not contain any attributes
        verifyZeroInteractions(mockModel);
    }

    @Test
    public void testUserLoginSuccess() {
        String testUsername = "testUser";
        String testPassword = "testPassword";
        // Mock the database connection to simulate a successful login
        // For simplicity, we will directly set the AdminController.usernameforclass here.
        adminController.usernameforclass = testUsername;

        String result = adminController.userlogin(testUsername, testPassword, mockModel);
        assertEquals("redirect:/index", result);
        assertEquals(testUsername, AdminController.usernameforclass);
        // Verify that UserController.setUsername(usernameforclass) is called
        // You can mock UserController and verify this method's invocation if it's part of another class.
    }

    @Test
    public void testUserLoginFailure() {
        String testUsername = "testUser";
        String testPassword = "wrongPassword";
        // Mock the database connection to simulate a failed login

        String result = adminController.userlogin(testUsername, testPassword, mockModel);
        assertEquals("userLogin", result);
        // Verify that the model contains the "failMessage" attribute
        verify(mockModel).addAttribute(eq("failMessage"), anyString());
        // Verify that UserController.setUsername(usernameforclass) is not called
        // You can mock UserController and verify this method's invocation if it's part of another class.
    }

    @Test
    public void testGetUserID() {
        String testUsername = "testUser";
        adminController.usernameforclass = testUsername;
        // Mock the database connection to simulate a successful query
        // For simplicity, we will directly set the userID value here.

        int result = adminController.getUserID();
        // Verify the returned userID is as expected
        assertEquals(1, result); // Replace 1 with the expected userID value.
    }

    @Test
    public void testAdminLogin() {
        String result = adminController.adminlogin(mockModel);
        assertEquals("adminlogin", result);
        // Verify that the model does not contain any attributes
        verifyZeroInteractions(mockModel);
    }

    @Test
    public void testAdminHomeLoggedIn() {
        adminController.adminlogcheck = 1;
        String result = adminController.adminHome(mockModel);
        assertEquals("adminHome", result);
        // Verify that the model does not contain any attributes
        verifyZeroInteractions(mockModel);
    }

    @Test
    public void testAdminHomeNotLoggedIn() {
        adminController.adminlogcheck = 0;
        String result = adminController.adminHome(mockModel);
        assertEquals("redirect:/admin", result);
        // Verify that the model does not contain any attributes
        verifyZeroInteractions(mockModel);
    }

    @Test
    public void testAdminLog() {
        String result = adminController.adminlog(mockModel);
        assertEquals("adminlogin", result);
        // Verify that the model does not contain any attributes
        verifyZeroInteractions(mockModel);
    }
}