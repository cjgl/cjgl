package cn.fy.cjgl.entity;

/**
 * 
 * @author Antonio
 *
 */
public class Result {
	/**
	 * 结果码 0成功 非0失败
	 */
	private String resultCode;

	/**
	 * 描述信息
	 */
	private String resultMsg;

	/**
	 * 返回Object
	 */
	private Object resultObject;

	public Result() {
	}

	public Result(String resultCode, String resultMsg) {
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Object getResultObject() {
		return resultObject;
	}

	public void setResultObject(Object resultObject) {
		this.resultObject = resultObject;
	}

}
