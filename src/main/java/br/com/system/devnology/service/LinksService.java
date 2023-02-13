package br.com.system.devnology.service;

import br.com.system.devnology.model.Links;
import br.com.system.devnology.repository.LinksRepository;
import br.com.system.devnology.exception.ObjectNotFountException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.*;
import java.util.Optional;

@Service
public class LinksService {

    @Autowired
    private LinksRepository linksRepository;


    @Transactional
    public Links insert(Links data){
        data.setId(null);
        data = linksRepository.save(data);
        return data;

    }

    public Links findId(Long id) {
        Optional<Links> links = linksRepository.findById(id);
        return links.orElseThrow(() -> new ObjectNotFountException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Links.class.getName()));
    }

    public Page<Links> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return linksRepository.findAll(pageRequest);

    }


    public Links update(long id, Links data) {
            Links entity = linksRepository.getOne(id);
            UpdateData(entity,data);
            return linksRepository.save(entity);
    }

    private void UpdateData(Links entity, Links data) {
        entity.setUrl(data.getUrl());
        entity.setTitle(data.getTitle());

    }

    public void delete(Long id) {
            findId(id);
            linksRepository.deleteById(id);
    }
}
