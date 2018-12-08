package hello;

import javax.servlet.AsyncContext;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(asyncSupported = true)
public  class TestAsync extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        public void doPost(HttpServletRequest req, HttpServletResponse res)
                throws IOException {
            AsyncContext asyncContext = req.startAsync();
            new Thread(() -> {
                ServletRequest request = asyncContext.getRequest();
                HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();

                System.out.println(Thread.currentThread());
                //  AsyncContext asyncContext = req.startAsync();
                ServletInputStream inputStream = null;
                try {
                    inputStream = request.getInputStream();
                ServletOutputStream outputStream = response.getOutputStream();
                int counr = 0;
                byte[] reads = new byte[20480];
                while (true) {
                    if (counr++ > 10000) {
                        break;
                    }
                    int read = inputStream.read(reads);
                    if (read != -1) {
                        outputStream.write(reads, 0, read);
                    } else {
                        break;
                    }
                }
                inputStream.close();
                outputStream.close();
                    asyncContext.complete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }