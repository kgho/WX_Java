package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.WxService;

/**
 * Servlet implementation class WxServlet
 */
@WebServlet("/wx")
public class WxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WxServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("get");
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		System.out.println(signature);
		System.out.println(timestamp);
		System.out.println(nonce);
		System.out.println(echostr);

		// У��֤����
		if (WxService.check(timestamp, nonce, signature)) {
			PrintWriter out = response.getWriter();
			// ԭ������echostr����
			out.print(echostr);
			out.flush();
			out.close();
			System.out.println("����ɹ�");
		} else {
			System.out.println("����ʧ��");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// doGet(request, response);
		System.out.println("post");

		request.setCharacterEncoding("utf8");
		response.setCharacterEncoding("utf8");

		// ������Ϣ���¼�������
		Map<String, String> requestMap = WxService.parseRequest(request.getInputStream());
		System.out.println(requestMap);

		// ׼���ظ����ݰ�
		String respXml = WxService.getResponse(requestMap);
		System.out.println(respXml);
		PrintWriter out = response.getWriter();
		out.print(respXml);
		out.flush();
		out.close();

	}

}
