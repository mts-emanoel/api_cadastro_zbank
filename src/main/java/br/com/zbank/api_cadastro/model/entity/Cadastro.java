package br.com.zbank.api_cadastro.model.entity;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.lang.NonNull;
import javax.persistence.*; //Entity, Id, GeneratedValue, GenerationType
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@Entity
public class Cadastro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCadastro;

    @NotBlank
    private String nome;

    @Email
    @Column(unique = true)
    private String email;

    @CPF
    @Column(unique = true)
    private String cpf;

    @Past
    @NonNull
    private Date nascimento;

    public Integer getIdCadastro() {
        return idCadastro;
    }

    public void setIdCadastro(Integer idCadastro) {
        this.idCadastro = idCadastro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        //Validação básica do Nome
        if (nome == null || nome.trim() == "") throw new IllegalArgumentException("Nome inválido");
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        //Declaração da Regex para validação do Email
        String emailRegexStr = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
        if (Pattern.compile(emailRegexStr).matcher(email).matches() == false)
            throw new IllegalArgumentException("Email inválido");
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        //Cálculo para validação do CPF
        try {
            if (cpf.trim().length() != 11) throw new IllegalArgumentException(); //Deve conter 12 dígitos

            //Cálculo do 1º digito verificador
            Integer dig1 = 0;
            for (int i = 10; i > 1; i--) dig1 += (Integer.parseInt(String.valueOf(cpf.charAt(10 - i))) * i);
            dig1 = (11 - (dig1 % 11));
            dig1 = (dig1==11 || dig1==10) ? 0 : dig1;

            //Cálculo do 2º digito verificador
            Integer dig2 = 0;
            for (int i = 11; i > 1; i--) dig2 += (Integer.parseInt(String.valueOf(cpf.charAt(11 - i))) * i);
            dig2 = (11 - (dig2 % 11));
            dig2 = (dig2==11 || dig2==10) ? 0 : dig2;

            //Verificação final
            if (Integer.parseInt(String.valueOf(cpf.charAt(9))) == dig1 && Integer.parseInt(String.valueOf(cpf.charAt(10))) == dig2)
                this.cpf = cpf;
            else throw new IllegalArgumentException();

        } catch (Exception e) {
            //Erros lançados dentro deste try{} serão tratados como CPF inválido
            throw new IllegalArgumentException("CPF inválido");
        }

    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        try {
            System.out.println(nascimento);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Formato de data de acordo com a ISO 8601
            Date dataNascimento = new SimpleDateFormat("yyyy-MM-dd").parse(nascimento);
            System.out.println(dataNascimento);
            this.setNascimento(dataNascimento);
        } catch (Exception e) {
            //Erros lançados dentro deste try{} serão tratados como Data de Nascimento inválida
            throw new IllegalArgumentException("Data de Nascimento inválida");
        }
    }

    public void setNascimento(@NonNull Date nascimento) {
        System.out.println(nascimento.toString());
        if (nascimento.before(new Date())) // Verifica apenas se a data indicada esta no passado
            this.nascimento = nascimento;
        else throw new IllegalArgumentException("Data de Nascimento inválida");
    }

}
