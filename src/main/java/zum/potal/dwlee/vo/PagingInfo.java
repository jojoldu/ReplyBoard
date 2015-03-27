package zum.potal.dwlee.vo;

public class PagingInfo {

	private int pageIndex = 1;// 현재 페이지
	private int pageSize = 10;// 한 페이지당 출력된 글 개수
	private int pageScope = 10; // 한번에 출력될 페이지수
	private int totalPageCount = 0;// 총 페이지 개수
	private int firstRow = 0;

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public void setPageScope(int pageScope) {
		this.pageScope = pageScope;
	}

	public int getPageScope() {
		return pageScope;
	}

	public int getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}

}
