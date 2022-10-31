package com.homework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
public class BookController {

    Book[] book = new Book[2];

    @Autowired
    HttpServletRequest request;
    HttpServletResponse response;
    public BookController(){
        String[] names={"共产党宣言","中华上下五千年"};
        String[] authors={"马克思、恩格斯","乙力 编译"};
        String[] publishs={"人民出版社","天地出版社"};
        String[] dates={"2015-01","2019-09"};
        int[] nums={3,10};

        for(int i=0;i<2;++i){
            book[i]=new Book(names[i],authors[i],publishs[i],dates[i],nums[i]);
        }
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/booklist")
    public String booklist(Model model){
        BookController bookControl = new BookController();
        model.addAttribute("name0",bookControl.book[0].name);
        model.addAttribute("author0",bookControl.book[0].author);
        model.addAttribute("publish0",bookControl.book[0].publish);
        model.addAttribute("date0",bookControl.book[0].date);
        model.addAttribute("number0",bookControl.book[0].num);
        model.addAttribute("name1",bookControl.book[1].name);
        model.addAttribute("author1",bookControl.book[1].author);
        model.addAttribute("publish1",bookControl.book[1].publish);
        model.addAttribute("date1",bookControl.book[1].date);
        model.addAttribute("number1",bookControl.book[1].num);
        return "booklist";
    }

    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping(value = "/getregist",method = POST)
    public String getRegist(String name, String author, String publish, String date, int num, @RequestParam("picture") MultipartFile picture, Model model)throws Exception{
        model.addAttribute("name",name);
        model.addAttribute("author",author);
        model.addAttribute("publish",publish);
        model.addAttribute("date",date);
        model.addAttribute("number",num);

        String originalFilename = picture.getOriginalFilename();
        String path = "/uploads";
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        String destFilePath = file.getAbsolutePath()+"\\"+originalFilename;
        File destFile = new File(destFilePath);
        model.addAttribute("path",destFilePath);
        picture.transferTo(destFile);
        return "getregist";
    }

    @RequestMapping(value = "/checklogin",method = POST)
    public String checklogin(String user, String psd)throws Exception{
        HttpSession session = request.getSession();
        session.setAttribute("user",user);
        session.setAttribute("psd",psd);
        return "redirect:checklogin2";
    }

    @RequestMapping(value = "/checklogin2")
    public String checklogin2(){

        return "redirect:booklist";
    }

    @RequestMapping(value = "/failed")
    public String failed(){
        return "failed";
    }
}

class Book{
    String name, author, publish, date;
    int num;

    public Book(String name, String author,String publish,String date,int num){
        this.name=name;
        this.author=author;
        this.publish=publish;
        this.date=date;
        this.num=num;
    }
}