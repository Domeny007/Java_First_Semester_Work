package controllers;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Command;
import utils.dao.*;

@WebServlet({"/calculator"})
public class CalculatorServlet extends HttpServlet {
	
	///
	private static final boolean DEBUG = false;
	///
	
	private static final long serialVersionUID = -2846969481980039273L;
	
	@Override
	public void init() throws ServletException {
		if (DEBUG) {
			System.err.println("[WARNING] - DEBUG flag is on!");
		} else {
			System.out.println("[INFO] - To enable debug mode just set to true flag DEBUG.");
		}
		super.init();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		session.setAttribute("sesMathFunction", null);
		session.setAttribute("sesNumber", null);
		processJSP(req, resp, new Command());
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		double first, second, answer = Double.NEGATIVE_INFINITY;
		Command c = new Command();
		String reqNumber = req.getParameter("digit");
		String reqMathFunction = req.getParameter("mathfunction");
		if (DEBUG) {
			System.out.print(reqNumber + " " + reqMathFunction + " ");
		}
		HttpSession session = req.getSession();
		Double sesNumber = (Double) session.getAttribute("sesNumber");
		String sesMathFunction = (String) session.getAttribute("sesMathFunction");
		if (reqNumber != null && reqMathFunction != null) {
			boolean isReqNumberValid = Pattern.matches("^-?[0-9]+.?[0-9]*$", reqNumber);
			boolean isReqMathFunctionValid = Pattern.matches("^[\\Q+-\\*/=\\E]$", reqMathFunction);
			boolean trigger = true;
			if (DEBUG) {
				System.out.println(isReqNumberValid + " " + isReqMathFunctionValid);
			}
			if (isReqNumberValid && isReqMathFunctionValid) {
				if (sesNumber == null) {
					answer = Double.valueOf(reqNumber);
				} else {
					first = Double.valueOf(sesNumber);
					second = Double.valueOf(reqNumber);
					if (!reqMathFunction.equals("=")) {
						if (reqMathFunction.equals("*")) {
							answer = first * second;
						} else if (reqMathFunction.equals("/")) {
							answer = first / second;
						} else if (reqMathFunction.equals("+")) {
							answer = first + second;
						} else if (reqMathFunction.equals("-")) {
							answer = first - second;
						}
					} else {
						if (sesMathFunction.equals("*")) {
							answer = first * second;
						} else if (sesMathFunction.equals("/")) {
							answer = first / second;
						} else if (sesMathFunction.equals("+")) {
							answer = first + second;
						} else if (sesMathFunction.equals("-")) {
							answer = first - second;
						} else if (sesMathFunction.equals("=")) {
							answer = second;
						}
						trigger = false;
					}
				}
				if (DEBUG) {
					System.out.println(
							answer + " " + reqNumber + " " + sesNumber + " " + sesMathFunction + " " + trigger);
				}
				Double leastAnswer = trigger ? answer : null;
				c.setNumber(leastAnswer);
				c.setMathFunction(reqMathFunction);
				setSessionAttribs(session, leastAnswer, reqMathFunction);
			} else if (!isReqNumberValid || !isReqMathFunctionValid) {
				clearOrder(c);
				setSessionAttribs(session, null, null);
			}
			c.setNumber(isReqNumberValid ? answer : null);
			c.setMathFunction(isReqMathFunctionValid ? reqMathFunction : null);
			processJSP(req, resp, c);
		} else {
			if (sesNumber == null || sesMathFunction == null) {
				clearOrder(c);
				setSessionAttribs(session, null, null);
				processJSP(req, resp, c);
			}
		}
	}
	
	private void setSessionAttribs(HttpSession session, Double number, String function) {
		session.setAttribute("sesNumber", number);
		session.setAttribute("sesMathFunction", function);
	}
	
	private void clearOrder(Command c) {
		c.setNumber(null);
		c.setMathFunction(null);
	}
	
	private void processJSP(HttpServletRequest req, HttpServletResponse resp, Command c) throws ServletException, IOException {
		req.setAttribute("command", c);
		if(c.getNumber() != null && c.getMathFunction() != null) {
			CommandDAO.INSTANCE.save(c);
		}
        getServletContext().getRequestDispatcher("/calculator.jsp").forward(req, resp);
	}
	
}
