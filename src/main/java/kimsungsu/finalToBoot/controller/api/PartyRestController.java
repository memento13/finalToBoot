package kimsungsu.finalToBoot.controller.api;

import kimsungsu.finalToBoot.entity.Party;
import kimsungsu.finalToBoot.entity.User;
import kimsungsu.finalToBoot.entity.form.Message;
import kimsungsu.finalToBoot.repository.PartyRepository;
import kimsungsu.finalToBoot.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;

@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
public class PartyRestController {

    private final PartyService partyService;
    private final PartyRepository partyRepository;

    /**
     * 파티 생성 컨트롤러 부분
     * 언젠가 모든걸 dto로 따로 만들어서 반환해야겠지...
     */
    @PostMapping("/create-party")
    public ResponseEntity<Message> createParty(HttpSession session, @RequestParam("party_name")String partyName){

        User user = (User) session.getAttribute("user");
        boolean createPartyResult = partyService.createParty(user, partyName);

        Message message = new Message();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        //파티생성 성공
        if(createPartyResult){
            Party party = partyRepository.findOneByName(partyName);
            message.setData(party);
            message.setMessage("파티 생성 성공");
            message.setStatus(HttpStatus.OK);
            return new ResponseEntity<>(message,httpHeaders, HttpStatus.OK);
        }
        else{ //실패
            message.setMessage("파티 생성 실패");
            message.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(message,httpHeaders, HttpStatus.BAD_REQUEST);
        }
    }
}
