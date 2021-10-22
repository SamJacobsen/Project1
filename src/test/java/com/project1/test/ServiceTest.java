package com.project1.test;

import com.project1.models.*;
import com.project1.services.Service;
import com.project1.services.ServiceImpl;
import com.project1.services.dao.ReimbursementDao;
import com.project1.services.dao.UserDao;
import com.project1.test.mocks.ReimbursementDaoMock;
import com.project1.test.mocks.UserDaoMock;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceTest {

    private Service service;
    private User mockUser;

    @Before
    public void init() {
        UserDao userMockDao = new UserDaoMock();
        ReimbursementDao reimbursementMockDao = new ReimbursementDaoMock();

        this.service = new ServiceImpl(userMockDao, reimbursementMockDao);
        this.mockUser = new User("test", "test", "test", UserType.EMPLOYEE);
    }

    @Test
    public void testValidateCredentials() {
        LoginRequest lrec = new LoginRequest();
        assertThat(this.service.validateCredentials(lrec), instanceOf(User.class));
    }

    @Test
    public void testPersistReimbursementTrue() {
        ReimbursementSafe r = new ReimbursementSafe();

        boolean response = this.service.persistReimbursement(r, this.mockUser);
        assertEquals(response, true);
    }

    @Test
    public void testPersistReimbursementFalse() {
        boolean response = this.service.persistReimbursement(null, this.mockUser);
        assertEquals(response, false);
    }

    @Test
    public void testGetReimbursementsAll() {
        assertNull(this.service.getReimbursements("ALL", this.mockUser));
    }

    @Test
    public void testGetReimbursementsOther() {
        assertNull(this.service.getReimbursements("PENDING", this.mockUser));
    }

    @Test
    public void testGetReimbursementsError() {
        assertNull(this.service.getReimbursements("r", null));
    }

    @Test
    public void testGetReimbursementsAllS() {
        List<ReimbursementFull> r = new ArrayList<>();
        r.add(new ReimbursementFull());

        assertEquals(this.service.getReimbursements("ALL").contains(new ReimbursementFull()), true);
    }

    @Test
    public void testGetReimbursementsOtherS() {
        List<ReimbursementFull> r = new ArrayList<>();
        r.add(new ReimbursementFull());

        assertEquals(this.service.getReimbursements("PENDING").contains(new ReimbursementFull()), true);
    }

    @Test
    public void testGetReimbursementsErrorS() {
        assertNull(this.service.getReimbursements(""));
    }

    @Test
    public void testGetFirstPendingReimbursement() {
        assertEquals(this.service.getFirstPendingReimbursement(), new ReimbursementFull());
    }

    @Test
    public void testUpdateReimbursement() {
        assertFalse(this.service.updateReimbursement(new ReimbursementFull()));
    }


}
