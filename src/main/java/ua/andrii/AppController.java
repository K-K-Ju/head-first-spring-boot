package ua.andrii;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.andrii.model.Customer;
import ua.andrii.repository.CustomerRepository;

import java.util.InputMismatchException;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class AppController {
    private final MyService service;
    private final CustomerRepository customerRepository;

    @Autowired
    AppController(MyService service, CustomerRepository customerRepository) {
        this.service = service;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/view")
    String getView() {
        return "view.html";
    }

    @GetMapping("/act")
    @ResponseBody
    String getActed(@RequestParam(name = "str") String str) {
        return service.doService(str);
    }

    @GetMapping("/all")
    @ResponseBody
    List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @PostMapping("/add")
    @ResponseBody
    Customer addCustomer(@RequestBody NewCustomerRequest request) {
        Customer customer = new Customer();
        customer.setAge(request.age());
        customer.setName(request.name());
        customer.setEmail(request.email());

        customerRepository.save(customer);

        return customer;
    }

    record NewCustomerRequest(
            String email,
            String name,
            int age
    ) {
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    String deleteCustomer(@PathVariable("id") Long id) {
        customerRepository.deleteById(id);
        return "success";
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    String updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerUpdateRequest request) {
        if (customerRepository.existsById(id)) {
            Customer dbCustomer = customerRepository.findById(id).orElseThrow(InputMismatchException::new);

            dbCustomer.setName(request.name);
            dbCustomer.setEmail(request.email);
            dbCustomer.setAge(request.age);

            customerRepository.save(dbCustomer);

            return "Success" + dbCustomer;
        } else {
            return "User doesn't exist!";
        }
    }

    record CustomerUpdateRequest(
            String name,
            String email,
            int age
    ) {
    }
}
