package it.unicam.cs.PAWNProjectBackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.Date;

@RelationshipProperties
@Data
@NoArgsConstructor
public class PrenotazioneDateFasciaOrariaOmbrelloneRel {

    @RelationshipId
    private Long id;

    @TargetNode
    private Ombrellone ombrellone;

    private Date data;

    private Integer fasciaOraria;

    public PrenotazioneDateFasciaOrariaOmbrelloneRel(Ombrellone ombrellone) {
        this.ombrellone = ombrellone;
    }

    public PrenotazioneDateFasciaOrariaOmbrelloneRel(Ombrellone ombrellone, Date data, Integer fasciaOraria) {
        this.ombrellone = ombrellone;
        this.data = data;
        this.fasciaOraria = fasciaOraria;
    }
}
