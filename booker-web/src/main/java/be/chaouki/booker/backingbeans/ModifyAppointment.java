package be.chaouki.booker.backingbeans;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ConversationScoped
public class ModifyAppointment implements Serializable {

	private static final long serialVersionUID = 3970819033597516605L;

	@Inject transient private Logger logger;
	@Inject private Conversation conversation;
     
    private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String beginMod(Integer id){
		this.id=id;
		conversation.begin();
		
		return "modify_appt";
	}
	
	public String finishMod(){
		conversation.end();
		
		return "index";
	}
	
	@PostConstruct
	private void init(){
		logger.info("Readying "+this.getClass().getName()+" bean");
	}
	
	@PreDestroy
	private void post(){
		logger.info("Destroying "+this.getClass().getName()+" bean");
	}

}