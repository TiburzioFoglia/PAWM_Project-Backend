package it.unicam.cs.PAWNProjectBackend.controller;

import it.unicam.cs.PAWNProjectBackend.model.*;
import it.unicam.cs.PAWNProjectBackend.repository.*;
import it.unicam.cs.PAWNProjectBackend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProvaController {
    /*private final ProvaService provaService;

    private final CoordinateRepository coordinateRepository;

    private final PointOfIntRepository pointOfIntRepository;

    private final PoiTypeRepository poiTypeRepository;

    private final EnteRepository enteRepository;
    private final AddressRepository addressRepository;

    private final TagRepository tagRepository;

    @GetMapping("/poi/all")
    public ResponseEntity<Collection<PointOfInterestNode>> getAllPois(){
        return ResponseEntity.ok(pointOfIntRepository.findAll());
    }

    @GetMapping("/category/all")
    public ResponseEntity<Collection<CategoryNode>> getCategoryAll(){
        return ResponseEntity.ok().body(provaService.getCategories());
    }

    @PostMapping("/poiType/all")
    public ResponseEntity<Collection<PoiType>> getPoiTypeFiltered(@RequestBody Collection<CategoryNode> filter){
        Collection<PoiType> result = provaService.getPoiTypes();
        if(filter.size()>0) result = provaService.getPoiTypes(filter);
        return ResponseEntity.ok().body(result);
    }

    //TODO: finire di completare il metodo
    @PostMapping("/ente/createPoi")
    public ResponseEntity<PointOfInterestNode> createPoi(@RequestBody Map<String, Object> body){
        Ente ente = enteRepository.findByUsername("ente1");
        String name = (String) body.get("name");
        String description = (String) body.get("description");
        Double lat = Double.parseDouble((String)body.get("lat"));
        Double lon = Double.parseDouble((String)body.get("lon"));
        Coordinate coordinate = new Coordinate(lat,lon);
        coordinateRepository.save(coordinate);
        String street = (String) body.get("street");
        Integer number = Integer.parseInt((String) body.get("number"));
        Address address = new Address(street,number);
        addressRepository.save(address);
        Collection<String> types = (Collection<String>) body.get("types");
        Collection<PoiType> poiTypes = new ArrayList<>();
        for (String type: types) {
            if(poiTypeRepository.findById(type).isPresent()) {
                poiTypes.add(poiTypeRepository.findById(type).get());
            }
        }
        Collection<Map<String,Object>> poiTagRels = (Collection<Map<String,Object>>) body.get("tags");
        Collection<AttivitaAttrezzaturaRel> values = new ArrayList<>();
        for (Map<String,Object> map:poiTagRels){
            String tag = (String)map.get("tag");
            TagNode tagNode = tagRepository.findById(tag).orElse(null);
            //AttivitaAttrezzaturaRel poiTagRel = new AttivitaAttrezzaturaRel(tagNode);
            if(!Objects.isNull(tagNode) && tagNode.getIsBooleanType()){
                Boolean value = Boolean.parseBoolean((String)map.get("value"));
                poiTagRel.setBooleanValue(value);
            }else poiTagRel.setStringValue((String)map.get("value"));
            values.add(poiTagRel);
        }
*//*        Collection<PoiTagRel> poiTagRelsColl = new ArrayList<>();
        Collections.addAll(poiTagRelsColl, poiTagRels);*//*
        PointOfInterestNode poi = provaService.createPoi(ente,name,description,address,coordinate,poiTypes,values);
        return Objects.isNull(poi) ? ResponseEntity.internalServerError().body(null) : ResponseEntity.ok(poi);
    }

    @PostMapping("/provaSer")
    public ResponseEntity<Gson> prova(@RequestBody Map<String,Object> body){
        Gson gson = new Gson();
        gson.fromJson(body.get("tags").toString(),TagNode.class); //TODO rivedere
        Collection<TagNode> tags = provaService.tagNodeCollectionFromJson(body.get("tags"));
        return ResponseEntity.ok(gson);
    }*/
}
