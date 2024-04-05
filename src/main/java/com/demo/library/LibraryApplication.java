package com.demo.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class LibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

}

@RestController
@RequestMapping("/books")
class RestApiDemoController {
	private List<Book> books = new ArrayList<>();

	public RestApiDemoController() {
		books.addAll(List.of(
				new Book("Spring Boot: Up and Running"),
				new Book("Podman In Action"),
				new Book("Terraform: Up and Running"),
				new Book("Container Security")
		));
	}

	@GetMapping
	Iterable<Book> getBooks() {
		return books;
	}

	@GetMapping("/{id}")
	Optional<Book> getBookById(@PathVariable String id) {
		for (Book c: books) {
			if (c.getId().equals(id)) {
				return Optional.of(c);
			}
		}

		return Optional.empty();
	}

	@PostMapping
	Book postBook(@RequestBody Book book) {
		books.add(book);
		return book;
	}

	@PutMapping("/{id}")
	ResponseEntity<Book> putBook(@PathVariable String id,
								 @RequestBody Book book) {
		int bookIndex = -1;

		for (Book c: books) {
			if (c.getId().equals(id)) {
				bookIndex = books.indexOf(c);
				books.set(bookIndex, book);
			}
		}

		return (bookIndex == -1) ?
				new ResponseEntity<>(postBook(book), HttpStatus.CREATED) :
				new ResponseEntity<>(book, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	void deleteBook(@PathVariable String id) {
		books.removeIf(c -> c.getId().equals(id));
	}
}

class Book {
	private final String id;
	private String name;

	public Book(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public Book(String name) {
		this(UUID.randomUUID().toString(), name);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
