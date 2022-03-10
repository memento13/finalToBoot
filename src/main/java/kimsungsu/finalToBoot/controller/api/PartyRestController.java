package kimsungsu.finalToBoot.controller.api;

import kimsungsu.finalToBoot.entity.Party;
import kimsungsu.finalToBoot.entity.User;
import kimsungsu.finalToBoot.entity.form.Message;
import kimsungsu.finalToBoot.repository.PartyRepository;
import kimsungsu.finalToBoot.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;

@RestController
@RequestMapping("/rest/parties")
@RequiredArgsConstructor
public class PartyRestController {

    private final PartyService partyService;
    private final PartyRepository partyRepository;

    /**
     * 파티 생성 컨트롤러 부분
     * 언젠가 모든걸 dto로 따로 만들어서 반환해야겠지...
     */
    @PostMapping("/{party_name}")
    public ResponseEntity<Message> createParty(HttpSession session, @PathVariable("party_name")String partyName){

        User user = (User) session.getAttribute("user");
        boolean createPartyResult = partyService.createParty(user, partyName);

        Message message = new Message();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        //캐시무효화
        CacheControl cacheControl = CacheControl.noCache().noStore().mustRevalidate();
        httpHeaders.setCacheControl(cacheControl);
        httpHeaders.setPragma("no-cache");

        //파티생성 성공
        if(createPartyResult){
            Party party = partyRepository.findOneByName(partyName);
            message.setData(party);
            message.setMessage("파티 생성 성공");
            message.setStatus(HttpStatus.CREATED);
            return new ResponseEntity<>(message,httpHeaders, HttpStatus.CREATED);
        }
        else{ //실패
            message.setMessage("파티 생성 실패");
            message.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(message,httpHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 내가 속하는 파티 목록 ( 리더인것과 멤버인것으로 나눠서)
     */
    @GetMapping("/")
    public ResponseEntity<Message> showPartyList(HttpSession session){
        User user = (User) session.getAttribute("user");

        Message message = new Message();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        //캐시무효화
        CacheControl cacheControl = CacheControl.noCache().noStore().mustRevalidate();
        httpHeaders.setCacheControl(cacheControl);
        httpHeaders.setPragma("no-cache");

        return null;
    }
}
