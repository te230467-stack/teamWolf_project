package com.example.book_reserve_project.service;

@Service
public class serviece {
    //すべての書籍を取得
    public List<Book> getAllBooks(){
        return repository.findAll();
    }
    //1件書籍取得
    public Optional<Book> getBookById(Long id){
        return bookRepository.findById(id);
    }
    // 書籍登録
    public Book createBook(Book book){
        return bookRepository.save(book);
    }
    

}