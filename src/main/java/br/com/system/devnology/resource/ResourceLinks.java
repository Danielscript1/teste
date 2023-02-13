package br.com.system.devnology.resource;

import br.com.system.devnology.model.Links;
import br.com.system.devnology.service.LinksService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value="/links")
public class ResourceLinks {

    @Autowired
    private LinksService linksService;

    @PostMapping
    public ResponseEntity<Links> insert(@RequestBody Links data){
        data = linksService.insert(data);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(data.getId()).toUri();
        return ResponseEntity.created(uri).body(data);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity<Links> findId(@PathVariable Long id) {
        Links links = linksService.findId(id);
        return ResponseEntity.ok().body(links);
    }

    @GetMapping
    public ResponseEntity<Page<Links>> findPage(
            @RequestParam(value="page",defaultValue = "0") Integer  page,
            @RequestParam(value="linesPerPage",defaultValue = "24") Integer linesPerPage,
            @RequestParam(value="orderBy",defaultValue = "title")String orderBy,
            @RequestParam(value="direction",defaultValue = "ASC")String direction) {
        Page<Links> list = linksService.findPage(page,linesPerPage,orderBy,direction);
        return ResponseEntity.ok().body(list);
    }

    @PutMapping(value= "/{id}")
    public ResponseEntity<Links> update(@PathVariable long id, @RequestBody Links data){
        Links updateLinks = linksService.update(id, data);
        return ResponseEntity.ok().body(updateLinks);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Links> delete(@PathVariable Long id){
        linksService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
