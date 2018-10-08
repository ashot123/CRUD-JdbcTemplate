package com.javainterviewpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeDAO employeeDAO;

    /**
     * Redirect to necessary mapping.`
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView dummy() {
        return new ModelAndView("redirect:/employees");
    }

    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public ModelAndView saveEmployee(@ModelAttribute("employee") Employee employee) {
        Integer id = employee.getId();
        if (id == null) {
            employeeDAO.saveEmployee(employee);
        } else {
            employeeDAO.updateEmployee(employee);
        }
        return new ModelAndView("redirect:/employees");
    }

    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editEmployee(@ModelAttribute("employee") Employee employee, @PathVariable("id") int id) {
        ModelAndView model = new ModelAndView("employees");

        employee = employeeDAO.getEmployeeById(id);
        List employeeList = employeeDAO.getAllEmployees();

        model.addObject("employee", employee);
        model.addObject("employeeList", employeeList);
        return model;
    }

    @RequestMapping(value = "/delete/{id}")
    public ModelAndView deleteEmployee(@ModelAttribute("employee") Employee employee, @PathVariable("id") int id) {
        employeeDAO.deleteEmployee(id);

        return new ModelAndView("redirect:/employees");
    }

    @RequestMapping(value = "/employees")
    public ModelAndView listEmployees(@ModelAttribute("employee") Employee employee) {
        ModelAndView model = new ModelAndView("employees");
        List employeeList = employeeDAO.getAllEmployees();
        model.addObject("employeeList", employeeList);

        return model;
    }
}