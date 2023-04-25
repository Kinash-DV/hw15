package dv.kinash.hw15.web;

import dv.kinash.hw15.dto.BookInfoDTO;
import dv.kinash.hw15.dto.BookInfoWithReaderDTO;
import dv.kinash.hw15.dto.BookNewDTO;
import dv.kinash.hw15.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookRestController {
    private final BookService bookService;

    @GetMapping("/admin/all")
    public List<BookInfoWithReaderDTO> getAll(){
        return bookService.getAllBook();
    }
    @PostMapping("/admin/add")
    @ResponseStatus(HttpStatus.CREATED)
    public BookInfoDTO addNewBook(@Valid @RequestBody BookNewDTO newBook){
        return bookService.addGetNewBook(newBook);
    }
    @DeleteMapping("/admin/{id}")
    public void deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
    }
    @GetMapping("/get_available")
    public List<BookInfoDTO> getAvailable(Principal principal){
        return bookService.getAvailableBook(principal.getName());
    }
    @GetMapping("/my")
    public List<BookInfoDTO> getMyBook(Principal principal){
        return bookService.getUserBook(principal.getName());
    }
    @GetMapping("/take/{id}")
    public void takeBook(@PathVariable Long id, Principal principal){
        bookService.takeBook(principal.getName(), id);
    }
    @GetMapping("/return/{id}")
    public void returnBook(@PathVariable Long id, Principal principal){
        bookService.returnBook(principal.getName(), id);
    }
}
