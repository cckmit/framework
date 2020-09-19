package org.mickey.framework.example.api;

import org.mickey.framework.core.api.BaseController;
import org.mickey.framework.example.po.Student;
import org.mickey.framework.example.service.student.IStudentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description
 *
 * @author wangmeng
 * @version 1.0.0
 * @ClassName StudentController.java
 * @Description TODO
 * @createTime 2020年06月19日 11:25:00
 */
@RestController
@RequestMapping("/api/student/")
public class StudentController extends BaseController<IStudentService, Student> {
}
