package kimsungsu.finalToBoot.entity.dto;

import kimsungsu.finalToBoot.entity.Party;
import lombok.Data;

@Data
public class PartyShowDTO {
    private String id;
    private String name;
    private Boolean isLeader;

    public PartyShowDTO(Party party,String userId) {
        this.id = party.getId();
        this.name = party.getName();
        if(userId.equals(party.getLeader().getId())){
            this.isLeader = true;
        }
        else{
            this.isLeader = false;
        }

    }
}


