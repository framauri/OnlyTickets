package com.example.demo.Controller;

import com.example.demo.Entity.Ticket;
import com.example.demo.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public String listTickets(Model model) {
        model.addAttribute("tickets", ticketService.getAllTickets());
        return "list-tickets"; // Thymeleaf template for ticket list
    }

    @GetMapping("/new")
    public String createTicketForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "ticket-form"; // Thymeleaf template for creating/editing ticket
    }

    @PostMapping("/save")
    public String saveTicket(@Valid @ModelAttribute Ticket ticket, BindingResult bindingResult, Model model) {
        // Check if there are validation errors
        if (bindingResult.hasErrors()) {
            // If there are errors, return to the form and show the error messages
            return "ticket-form";
        }

        // If no validation errors, save the ticket
        ticketService.saveTicket(ticket);
        return "redirect:/tickets";
    }

    @GetMapping("/edit/{id}")
    public String editTicketForm(@PathVariable Long id, Model model) {
        Ticket ticket = ticketService.getTicketById(id);
        model.addAttribute("ticket", ticket);
        return "ticket-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return "redirect:/tickets";
    }
}
