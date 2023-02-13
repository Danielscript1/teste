package br.com.system.devnology;

import br.com.system.devnology.model.Links;
import br.com.system.devnology.resource.ResourceLinks;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class DevnologyApplicationTests {

	@Autowired
	ResourceLinks resourceLinks;

	@Test
	@Order(1)
	public void shouldSavedLinkBancoTest() {

		Links savedLinks = resourceLinks.insert(new Links(1l,"urlteste.com","titulo")).getBody();
		Assertions.assertThat(savedLinks.getId()).isGreaterThan(0);

	}

	@Test
	@Order(2)
	public void shouldSearchLinkIdTest(){

		Links savedLinks = resourceLinks.insert(new Links(1l,"urlteste.com","titulo")).getBody();
		Links links = resourceLinks.findId(1L).getBody();
		Assertions.assertThat(links.getId()).isEqualTo(1L);

	}

	@Test
	@Order(3)
	@Rollback(value = false)
	public void shouldUpdateLinkTest(){

		Links savedLinks = resourceLinks.insert(new Links(1l,"urlteste.com","titulo")).getBody();
		Links links = resourceLinks.findId(1L).getBody();
		links.setTitle("novo titulo");
		Links link = resourceLinks.insert(links).getBody();
		Assertions.assertThat(link.getTitle()).isEqualTo("novo titulo");

	}

	@Test
	@Order(4)
	public void shouldSearchPageLinkAllTest(){
		Links savedLinks = resourceLinks.insert(new Links(1l,"urlteste.com","titulo")).getBody();
		ResponseEntity<Page<Links>> links = resourceLinks.findPage(0, 24, "title", String.valueOf(Sort.Direction.ASC));
		Assertions.assertThat(links.getBody().getSize()).isEqualTo(24);
		Assertions.assertThat(links.getBody().hasContent()).isEqualTo(links.getBody().getSize()>0);

	}

	@Test
	@Order(5)
	@Rollback(false)
	public void shouldDeleteProductTest() {

		Links link = resourceLinks.insert(new Links(1l,"urlteste.com","titulo")).getBody();
		Links linkDelete = resourceLinks.findId(1l).getBody();
		Links delete = resourceLinks.delete(linkDelete.getId()).getBody();
		Assertions.assertThat(delete).isNull();

	}

}
