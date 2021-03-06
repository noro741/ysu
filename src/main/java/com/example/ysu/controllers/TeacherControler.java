package com.example.ysu.controllers;

import com.example.ysu.entitys.Facultat;
import com.example.ysu.entitys.Standart;
import com.example.ysu.entitys.Teacher;
import com.example.ysu.interfaces.com.Config;
import com.example.ysu.interfaces.com.TeacherInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RequestMapping("/teacher")
@Controller
public class TeacherControler  {

    @Autowired
    TeacherInterface teacherInterface;

    @Autowired
    FacultatInterfae facultatInterfae;


    public static  class NewTeacher{
        private Long id;
        private Standart standart;
        private List<String> facultates;


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Standart getStandart() {
            return standart;
        }

        public void setStandart(Standart standart) {
            this.standart = standart;
        }

        public List<String> getFacultates() {
            return facultates;
        }

        public void setFacultates(List<String> facultates) {
            this.facultates = facultates;
        }

        public NewTeacher() {
        }
    }

    @RequestMapping(value = "/edit" , method = RequestMethod.POST)
    public @ResponseBody void update(@RequestBody NewTeacher newTeacher){

       List<Long> longList= teacherInterface.findJoin();

       Teacher teacher=teacherInterface.findOne(newTeacher.getId());
       teacher.setStandart(newTeacher.getStandart());
       List<Facultat> facultatList= (List<Facultat>) facultatInterfae.findAll();

       for (int i=0;i<facultatList.size();i++){
           for (int k=0;k<longList.size();k++){
               if (facultatList.get(i).getId()==longList.get(k)){
                   facultatList.get(i).setName(newTeacher.getFacultates().get(k));
                   break;
               }
           }
       }
       teacher.setFacultatList(facultatList);

        teacherInterface.save(teacher);
        System.out.println(teacherInterface.findJoin());
    }

    @RequestMapping(value = "/insert" , method = RequestMethod.POST)
    public @ResponseBody void insert(@RequestBody NewTeacher newTeacher){
        System.out.println(newTeacher.facultates.get(0));
        Teacher teacher=new Teacher();
        teacher.setStandart(newTeacher.getStandart());
        List<Facultat> facultatList=new ArrayList<>();
        for (int i=0;i<newTeacher.getFacultates().size();i++){
            Facultat facultat=new Facultat();
            facultat.setName(newTeacher.getFacultates().get(i));
            facultatList.add(facultat);
        }
        teacher.setFacultatList(facultatList);
        teacherInterface.save(teacher);

    }
}
