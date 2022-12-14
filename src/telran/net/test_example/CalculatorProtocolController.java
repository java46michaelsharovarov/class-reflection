package telran.net.test_example;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

import telran.net.common.ApplProtocol;
import telran.net.common.Request;
import telran.net.common.Response;
import telran.net.common.ResponseCode;

public class CalculatorProtocolController implements ApplProtocol {
	
	CalculatorService calculatorService;
	
	public CalculatorProtocolController(CalculatorService calculator) {
		this.calculatorService = calculator;
	}
	
	@Override
	public Response handlRequest(Request request) {
		Response response;
		try {
			Method method = this.getClass().getDeclaredMethod(request.requestType, double[].class);
			response = (Response) method.invoke(this, getArguments(request.requestData));
		} catch (NoSuchMethodException e) {
			response = new Response(ResponseCode.WRONG_REQUEST_TYPE, "unknown method: " + request.requestType);
		} catch (Exception e) {
			response = new Response(ResponseCode.WRONG_REQUEST_DATA, e.getMessage());
		}
		return response;
	}
	
	Response add(double [] operands) {
		double res = calculatorService.add(operands[0], operands[1]);
		return new Response(ResponseCode.OK, res);
	}
	
	Response subtract(double [] operands) {
		double res = calculatorService.subtract(operands[0], operands[1]);
		return new Response(ResponseCode.OK, res);
	}
	
	Response divide(double [] operands) {
		double res = calculatorService.divide(operands[0], operands[1]);
		return new Response(ResponseCode.OK, res);
	}
	
	Response multiply(double [] operands) {
		double res = calculatorService.multiply(operands[0], operands[1]);
		return new Response(ResponseCode.OK, res);
	}
	
	private double[] getArguments(Serializable requestData) throws Exception {
		try {
			double[] res = (double[]) requestData;
			System.out.println(Arrays.toString(res));
			if (res.length != 2) {
				throw new Exception("no two operands");
			}
			return res;
		} catch (ClassCastException e) {
			throw new Exception("no array of doubles");
		}		
	}

}