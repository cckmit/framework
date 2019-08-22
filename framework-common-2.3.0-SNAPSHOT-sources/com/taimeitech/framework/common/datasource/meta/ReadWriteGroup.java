package com.taimeitech.framework.common.datasource.meta;

public class ReadWriteGroup {

	private String id;

	private DataSourceMetaInfo writer;

	private SmoothBalancer<DataSourceMetaInfo, DataSourceMetaInfo> readerBalancer = new SmoothBalancer<>();

	public ReadWriteGroup(String id) {
		this.id = id;
	}

	public DataSourceMetaInfo getWriter() {
		return writer;
	}

	public void setWriter(DataSourceMetaInfo writer) {
		this.writer = writer;
	}

	public void addReader(DataSourceMetaInfo readerInfo) {
		readerBalancer.add(readerInfo, readerInfo, readerInfo.getWeight());
	}

	public DataSourceMetaInfo nextReader() {
		DataSourceMetaInfo next = readerBalancer.next();
		return next == null ? writer : next;
	}

	public String getId() {
		return id;
	}

	public SmoothBalancer<DataSourceMetaInfo, DataSourceMetaInfo> getReaderBalancer() {
		return readerBalancer;
	}

	//when allocate a group, we only care about write
	public int getWeight() {
		return writer == null ? 0 : writer.getWeight();
	}
}
