package kimsungsu.finalToBoot.controller.api;

import kimsungsu.finalToBoot.entity.Party;
import kimsungsu.finalToBoot.entity.User;
import kimsungsu.finalToBoot.entity.dto.PartyShowDTO;
import kimsungsu.finalToBoot.entity.form.Message;
import kimsungsu.finalToBoot.repository.PartyRepository;
import kimsungsu.finalToBoot.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.util.List;

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
     * 이름으로 파티 정보 조회함수
     * 유저가 속해있는 파티인 경우 파티 이름,id,리더 여부를 알려줌
     * 속해있는 파티가 아닌 경우, 해당 파티가 없는 경우 404 에러
     */
    @GetMapping("/{party_name}")
    public ResponseEntity<Message> partyInfo(HttpSession session, @PathVariable("party_name")String partyName){
        User user = (User) session.getAttribute("user");

        Message message = new Message();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        //캐시무효화
        CacheControl cacheControl = CacheControl.noCache().noStore().mustRevalidate();
        httpHeaders.setCacheControl(cacheControl);
        httpHeaders.setPragma("no-cache");

        PartyShowDTO result = null;
        result = partyService.showPartyInfoIfUserJoin(user, partyName);

        if(result == null){
            message.setData(result);
            message.setMessage("조회 실패 : 파티가 없거나 유저가 파티에 속해있지 않는 상태입니다.");
            message.setStatus(HttpStatus.NOT_FOUND);
        }
        else{
            message.setData(result);
            message.setMessage("조회성공");
            message.setStatus(HttpStatus.OK);
        }

        return new ResponseEntity<>(message,httpHeaders,message.getStatus());

    }

    /**
     * 내가 리더인 파티 목록
     * 로그인확인용 인터셉터가 없어서 아직 정상작동x
     * 인터셉터 작동시 401 에러로 내보냄
     */
    @GetMapping("/leader")
    public ResponseEntity<Message> showLeaderPartyList(HttpSession session){

        //try catch 문이 필요한가?
        User user = (User) session.getAttribute("user");

        Message message = new Message();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        //캐시무효화
        CacheControl cacheControl = CacheControl.noCache().noStore().mustRevalidate();
        httpHeaders.setCacheControl(cacheControl);
        httpHeaders.setPragma("no-cache");

        List<PartyShowDTO> result = partyService.partiesWhereIamLeaderV2(user);
        message.setData(result);
        message.setMessage("조회성공");
        message.setStatus(HttpStatus.OK);

        return new ResponseEntity<>(message,httpHeaders,HttpStatus.OK);
    }

    /**
     * 내가 멤버인 파티 목록
     */
    @GetMapping("/member")
    public ResponseEntity<Message> showMemberPartyList(HttpSession session){
        //try catch 문이 필요한가?
        User user = (User) session.getAttribute("user");

        Message message = new Message();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        //캐시무효화
        CacheControl cacheControl = CacheControl.noCache().noStore().mustRevalidate();
        httpHeaders.setCacheControl(cacheControl);
        httpHeaders.setPragma("no-cache");

        List<PartyShowDTO> result = partyService.partiesWhereIamMemberV2(user);
        message.setData(result);
        message.setMessage("조회성공");
        message.setStatus(HttpStatus.OK);

        return new ResponseEntity<>(message,httpHeaders,HttpStatus.OK);
    }

    /**
     * 파티 가입
     */
    @PostMapping("/join")
    public ResponseEntity<Message> partyJoin(HttpSession session,
                                             @RequestParam(value = "party_name",required = true)String partyName){
        User user = (User) session.getAttribute("user");

        Message message = new Message();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        //캐시무효화
        CacheControl cacheControl = CacheControl.noCache().noStore().mustRevalidate();
        httpHeaders.setCacheControl(cacheControl);
        httpHeaders.setPragma("no-cache");

        Boolean result = partyService.partyJoinByPartyName(user, partyName);
        if(result){
            message.setMessage("가입성공");
            message.setStatus(HttpStatus.CREATED);
        }
        else{
            message.setMessage("가입실패");
            message.setStatus(HttpStatus.NOT_FOUND);
        }

        message.setData(result);

        return new ResponseEntity<>(message,httpHeaders,message.getStatus());

    }

    /**
     * 구현해야 하는 기능
     * 파티 탈퇴
     * 파티 삭제
     * 파티 검색
     */
}
