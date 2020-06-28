package com.tck.restapp;

import com.tck.aop.AdminTokenRequired;
import com.tck.aop.UserTokenRequired;
import com.tck.model.User;
import com.tck.service.TicketService;
import com.tck.service.UserService;
import com.tck.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    UserService userService;

    @Autowired
    TicketService ticketService;

    @ResponseBody
    @AdminTokenRequired
    @RequestMapping("/by/admin")
    public <T> T getAllTickets(HttpServletRequest request) {
        return (T) Util.getSuccessResult(ticketService.getAllTickets());
    }

    @ResponseBody
    @CSRTockenRequired
    @RequestMapping("/by/csr")
    public <T> T getAllTicketsByCSR(HttpServletRequest request) {
        return (T) Util.getSuccessResult(ticketService.getAllTickets());
    }

    /*
     * Rule :
     * Only user can create a ticket
     */

    @SuppressWarnings("unchecked")
    @ResponseBody
    @UserTokenRequired
    @RequestMapping(value="", method=RequestMethod.POST)
    public <T> T addTicket(
            @RequestParam(value="content") String content,
            HttpServletRequest request
    ) {
        User user = userService.getUserByToken(request.getHeader("token"));
        ticketService.addTicket(user.getUserid(), content, 2, 1);
        return Util.getSuccessResult();
    }
}
