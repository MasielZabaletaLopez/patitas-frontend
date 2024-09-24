package pe.edu.cibertec.patitas_frontend_a.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import pe.edu.cibertec.patitas_frontend_a.viewmodel.LoginModel;
import pe.edu.cibertec.patitas_frontend_a.viewmodel.LoginRequestDTO;

@Controller
@RequestMapping("/login")

public class LoginController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/inicio")
    public String inicio(Model model) {
        LoginModel loginModel = new LoginModel("00", "", "", "");
        model.addAttribute("loginModel", loginModel);
        return "inicio";
    }

    @PostMapping("/autenticar")
    public String autenticar(@RequestParam("tipoDocumento") String tipoDocumento,
                             @RequestParam("numeroDocumento") String numeroDocumento,
                             @RequestParam("password") String password,
                             Model model) {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(tipoDocumento, numeroDocumento, password);

        ResponseEntity<LoginModel> loginModelResponse = restTemplate.postForEntity("http://localhost:8080/autenticacion/login",loginRequestDTO, LoginModel.class);

        if (!loginModelResponse.getBody().codigo().equals("00")){
            model.addAttribute("loginModel", loginModelResponse.getBody());
            return "inicio";
        }

        model.addAttribute("loginModel", loginModelResponse.getBody());
        return "principal";
    }
    }
