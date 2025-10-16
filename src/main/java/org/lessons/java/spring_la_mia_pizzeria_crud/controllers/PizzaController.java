package org.lessons.java.spring_la_mia_pizzeria_crud.controllers;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/pizzas")
public class PizzaController {
    
    @Autowired
    private PizzaRepository repository;

    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("list", repository.findAll());
        
        return "pizzas/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model){

        model.addAttribute("pizza", repository.findById(id).get());

        return "pizzas/pizzadetail";
    }

    @GetMapping("/searchByName")
    public String searchByName(@RequestParam(name="name") String name, Model model){

        model.addAttribute("list", repository.findByNameContaining(name));

        
        return "pizzas/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "pizzas/newPizza";
    }

    @PostMapping("/create")
    public String strore(@Valid @ModelAttribute("pizza") Pizza formPizza,
    BindingResult bindingResult, Model model ) {
        if (bindingResult.hasErrors()) {
            return"pizzas/newPizza";
        }
        repository.save(formPizza);
        return "redirect:/pizzas/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id,  Model model) {
        model.addAttribute("pizza", repository.findById(id).get());
        return "pizzas/editPizza";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza formPizza,
    BindingResult bindingResult, Model model ) {
        if (bindingResult.hasErrors()) {
            return"pizzas/editPizza";
        }
        repository.save(formPizza);
        return "redirect:/pizzas/";
    }
    
    
}
