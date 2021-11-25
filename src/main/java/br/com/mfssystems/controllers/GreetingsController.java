package br.com.mfssystems.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.mfssystems.model.Usuario;
import br.com.mfssystems.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	@Autowired /*CI/CD ou CDI - Injeção de dependencia */
	private UsuarioRepository usuarioRepository ;
	
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "/mostrar-nome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Curso Spring Boot API " + name + "!";
    }
    
    @RequestMapping(value ="/olamundo/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String retornaOlaMundo(@PathVariable String nome ) {
    	Usuario usuario = new Usuario() ;
    	usuario.setNome(nome) ;
    	usuarioRepository.save(usuario );
    	
    	return("Ola Mundo "+nome) ;
    	
    }
    
    @GetMapping(value = "lista-todos")
    @ResponseBody /* Retorna os dados para o corpo da resposta em forma de json */
    public ResponseEntity< List <Usuario> > ListarUsuarios() {
    	
    	List<Usuario> usuarios = usuarioRepository.findAll();
    	
    	return new ResponseEntity< List<Usuario>>(usuarios, HttpStatus.OK);
    	
    }
    @PostMapping(value = "salvar-usuario")
    @ResponseBody /* Descrição da resposta */
    public ResponseEntity< Usuario > salvar(@RequestBody Usuario usuario) {   /*Recebe os dados para Salvar */
    	
    	Usuario user = usuarioRepository.save( usuario );
    	
    	return new ResponseEntity< Usuario>(user, HttpStatus.CREATED );
    	
    }
    
    @DeleteMapping(value = "delete-usuario")
    @ResponseBody /* Descrição da resposta */
    public ResponseEntity< String > salvar(@RequestParam Long id ) {   /*Recebe o id para deletar */
    	
    	usuarioRepository.deleteById( id );
    	
    	return new ResponseEntity< String >("Usuário deletado com sucesso", HttpStatus.OK );
    	
    }
    
    @GetMapping(value = "buscar-usuario")
    @ResponseBody /* Descrição da resposta */
    public ResponseEntity< Usuario > buscarUsuario(@RequestParam(name="id") Long id ) {   /*Recebe o id para retornar o usuario */
    	
    	Usuario usuario = usuarioRepository.findById( id ).get();
    	
    	return new ResponseEntity< Usuario>(usuario, HttpStatus.OK );
    	
    }
    
    @PutMapping(value = "atualizar-usuario")
    @ResponseBody /* Descrição da resposta */
    public ResponseEntity< ? > atualizarUsuario(@RequestBody Usuario usuario) {   /*Recebe os dados para Salvar */
    	
    	if ( usuario.getId() == null ) {
    		
    		return new ResponseEntity< String >("Id Não foi informado para atualização.", HttpStatus.BAD_REQUEST);
    		
    	} else {
    		
		     Usuario user = usuarioRepository.saveAndFlush( usuario );
		    	
		     return new ResponseEntity< Usuario>(user, HttpStatus.OK);
    	}
    }
    
    @GetMapping(value = "buscarPorNome")
    @ResponseBody /* Descrição da resposta */
    public ResponseEntity< List<Usuario> > buscarPorNome(@RequestParam(name="nome") String nome) {   /*Recebe o nome para retornar os usuario */
    	
    	List<Usuario> usuario = usuarioRepository.buscarPorName( nome.trim().toUpperCase() );
    	
    	return new ResponseEntity< List<Usuario>>(usuario, HttpStatus.OK );
    	
    }
}
