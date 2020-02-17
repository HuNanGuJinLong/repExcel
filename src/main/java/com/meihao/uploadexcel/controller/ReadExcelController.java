package com.meihao.uploadexcel.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meihao.uploadexcel.entity.Messages;
import com.meihao.uploadexcel.service.MessagesService;
import com.meihao.uploadexcel.util.ExportExcelUtil;
import com.meihao.uploadexcel.util.POIUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/go")
public class ReadExcelController {
    private static Logger logger = Logger.getLogger(ReadExcelController.class);
    String path=null;
    ArrayList<Messages> messages = new ArrayList<>();
    String s = null;
    @Autowired
    private MessagesService messagesService;

    @GetMapping("/goto")
    public String GoToLogin() {
        return "upload";
    }

    @GetMapping("/end")
    public String Goend() {
        return "end";
    }

    @RequestMapping("/readExcel")
    public String readExcel(@RequestParam(value = "excelFile") MultipartFile excelFile, HttpServletRequest req, HttpServletResponse resp, Model model) {
        s = excelFile.getOriginalFilename();
        System.out.println(s);
        if (s != null) {
            if (s.endsWith(".xlsx") || s.endsWith(".xls")) {
                try {
                    List<String[]> list = POIUtil.readExcel(excelFile, model);
                    if (list.size() == 0) {
                        return "error";
                    } else {
                        for (int i = 0; i < list.size(); i++) {
                            String[] strings = list.get(i);
                            Messages messages1 = new Messages();
                            try {
                                messages1.setCollege(strings[0]);
                                messages1.setClasses(strings[1]);
                                messages1.setBookId(strings[2]);
                                messages1.setBookName(strings[3]);
                                messages1.setReservationNumber(strings[4]);
                                messages.add(messages1);
                            } catch (Exception e) {
                                System.out.println(e);
                                logger.info(e);
                            }
                        }
                      //  this.writeExcelq(req,messages,s);

                        return "success";
                    }
                } catch (IOException e) {
                    logger.info("读取excel文件失败", e);
                    return "error";
                }
            } else {
                return "format";
            }
        } else {
            return "format";
        }
    }

    /**
     * 重新编译文件
     * @param
     * @param
     * @return
     */
    @RequestMapping("/writeExcel")
    public String writeExcel(String result, HttpServletRequest req) {
        for (Messages message : messages) {
            System.out.println(message);
        }
        File file = new File(s);
         path = file.getAbsolutePath();
        System.out.println("path:"+path);
        if ("yes".equals(result)) {
            //将数据重新打包成excel
            try {
                String[] columnNames = { "学院", "班级","书号", "教材名称", " 预定数量（本）"};
                new ExportExcelUtil().exportExcel("测试", columnNames, messages, new FileOutputStream(path), ExportExcelUtil.EXCEL_FILE_2003);
                messages.clear();
                logger.info("数据已经重新生成表格");
             /*   //转json
                String str = JSON.toJSON(messages).toString();
                List<Messages> messages1 = JSONObject.parseArray(str, Messages.class);
                messagesService.insertLists(messages1);
                logger.info("数据已经写入数据库");*/

                return "end";
            } catch (FileNotFoundException e) {
                logger.info("数据在生成表格的时候出现异常：" + e);
                e.printStackTrace();
                return "error";
            }
        } else {
            return "upload";
        }
    }
   /* public String writeExcelq( HttpServletRequest req,List messages,String s) {
        File file = new File(s);
        path = file.getAbsolutePath();
        System.out.println("path:"+path);
            //将数据重新打包成excel
            try {
                String[] columnNames = { "学院", "班级","书号", "教材名称", " 预定数量（本）"};
                new ExportExcelUtil().exportExcel("测试", columnNames, messages, new FileOutputStream(path), ExportExcelUtil.EXCEL_FILE_2003);
                logger.info("数据已经重新生成表格");
              *//*  //转json
                String str = JSON.toJSON(messages).toString();
                List<Messages> messages1 = JSONObject.parseArray(str, Messages.class);
                messagesService.insertLists(messages1);
                logger.info("数据已经写入数据库");*//*

                return "end";
            } catch (FileNotFoundException e) {
                logger.info("数据在生成表格的时候出现异常：" + e);
                e.printStackTrace();
                return "error";
            }

    }*/




   /* public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        try {
            // 打开文件
            // 获取到要下载文件的全路径
            // 得到要下载的文件名，小伙伴可以根据自己的实际文件名更改，这里是博主自己定义的文件名
            String destinationfileName = request.getSession().getAttribute("destinationfileName").toString();
            destinationfileName = new String(destinationfileName.getBytes("iso8859-1"), "utf-8");
            // 得到要下载的文件的所在目录，同上，小伙伴可以根据自己项目更改内容
            String uploadpath = request.getSession().getAttribute("uploadPath").toString();

            // 得到要下载的文件
            File file = new File(uploadpath + "\\" + destinationfileName);

            //如果文件不存在，则显示下载失败
            if (!file.exists()) {
                request.setAttribute("message", "下载失败");
                request.getRequestDispatcher("/WEB-INF/UploadPSucceed.jsp").forward(request, response);
                return;
            } else {

                // 设置相应头，控制浏览器下载该文件，这里就是会出现当你点击下载后，出现的下载地址框
                response.setHeader("content-disposition",
                        "attachment;filename=" + URLEncoder.encode(destinationfileName, "utf-8"));
                // 读取要下载的文件，保存到文件输入流
                FileInputStream in = new FileInputStream(uploadpath + "\\" + destinationfileName);
                // 创建输出流
                OutputStream out = response.getOutputStream();
                // 创建缓冲区
                byte buffer[] = new byte[1024];
                int len = 0;
                // 循环将输入流中的内容读取到缓冲区中
                while ((len = in.read(buffer)) > 0) {
                    // 输出缓冲区内容到浏览器，实现文件下载
                    out.write(buffer, 0, len);
                }
                // 关闭文件流
                in.close();
                // 关闭输出流
                out.close();
            }
        } catch (Exception e) {
            // TODO: handle exception

        }

    }*/

/*    public void downLoad(String filePath, HttpServletResponse response)
            throws Exception {
        System.out.println("filePath"+filePath);
        File f = new File(filePath);
        if (!f.exists()) {
            response.sendError(404, "File not found!");
            return;
        }
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
        byte[] buf = new byte[1024];
        int len = 0;
        response.reset();
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
        OutputStream out = response.getOutputStream();
        while ((len = br.read(buf)) > 0) out.write(buf, 0, len);
        br.close();
        out.close();
    }*/
    //响应流给前端可以唤醒下载
    @RequestMapping("/writeExcels")
    public void download(HttpServletRequest request,HttpServletResponse response) {
        //所要下载的文件路径，从数据库中查询得到，当然也可以直接写文件路径，如：C:\\Users\\Administrator\\Desktop\\csv\\号码_utf8_100.csv
        String filePath = new File("").getAbsolutePath();
        OutputStream out=null;
        FileInputStream in=null;
        try {
            File file = new File(path);
            String fileName = path.substring(path.lastIndexOf(File.separator)+1);//得到文件名
            fileName = new String(fileName.getBytes("UTF-8"),"ISO8859-1");//把文件名按UTF-8取出并按ISO8859-1编码，保证弹出窗口中的文件名中文不乱码，中文不要太多，最多支持17个中文，因为header有150个字节限制。
            response.setContentType("application/octet-stream");//告诉浏览器输出内容为流
            response.addHeader("Content-Disposition", "attachment;filename="+fileName);//Content-Disposition中指定的类型是文件的扩展名，并且弹出的下载对话框中的文件类型图片是按照文件的扩展名显示的，点保存后，文件以filename的值命名，保存类型以Content中设置的为准。注意：在设置Content-Disposition头字段之前，一定要设置Content-Type头字段。
            String len = String.valueOf(file.length());
            response.setHeader("Content-Length", len);//设置内容长度
             out = response.getOutputStream();
             in = new FileInputStream(file);
            byte[] b = new byte[1024];
            int n;
            while((n=in.read(b))!=-1){
                out.write(b, 0, n);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (in!=null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
              if (out!=null){
                  out.close();
              }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

/*    public static void downZip(String filename,String filepath,HttpServletResponse response) {
        FileInputStream inStream=null;
        try {
            File zip=new File(filepath);// 文件
            inStream=new FileInputStream(zip);
            byte[] buf=new byte[4096];
            int readLength;
            setResponseHeader(response, filename);
            while (((readLength=inStream.read(buf)) != -1)) {
                response.getOutputStream().write(buf, 0, readLength);
            }
        }catch (Exception e){
            try {
                OutputStream outputStream=response.getOutputStream();//获取OutputStream输出流
                response.setHeader("content-type", "text/html;charset=UTF-8");//通过设置响应头控制浏览器以UTF-8的编码显示
                byte[] dataByteArr="下载失败".getBytes("UTF-8");//将字符转换成字节数组，指定以UTF-8编码进行转换
                outputStream.write(dataByteArr);//使用OutputStream流向客户端输出字节数组
                return;
            }catch (Exception ex){
            }
        }finally {
            try {
                inStream.close();
            } catch (Exception e) {

            }
        }
    }

    *//**
     * 设置响应头  文件类型为zip的   可以修改对应的后缀
     * @param response
     * @param fileName
     *//*

    public static void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            response.reset();// 清空输出流
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String(fileName.getBytes("GB2312"), "8859_1")
                    + ".xlsx");
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }*/
}
