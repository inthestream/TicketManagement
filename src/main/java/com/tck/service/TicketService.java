package com.tck.service;

import com.tck.model.Ticket;
import com.tck.model.User;

import java.util.List;

public interface TicketService {
    List<Ticket> getAllTickets();
    List<Ticket> getMyTickets(Integer creatorid);
    void deleteMyTicket(Integer userid, Integer ticketid);
    Ticket getTicket(Integer creatorid, Integer ticketid);
    Ticket getTicket(Integer ticketid);

    void addTicket(Integer creatorid, String content, Integer severity, Integer status);
    void updateTicket(Integer ticketid, String content, Integer severity, Integer status);
    void deleteTickets(User user, String ticketids);

}
