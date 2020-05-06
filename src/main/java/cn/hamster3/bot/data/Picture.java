package cn.hamster3.bot.data;

/**
 * 消息中所携带的图片或表情。
 */
public class Picture {
    private String path;
    private String url;
    private long fileID;
    private int fileSize;
    private String fileMD5;
    private String forwordBuf;
    private String forwordField;

    /**
     * 这个构造方法一般用于好友发过来的图片消息
     *
     * @param path     原图URL
     * @param url      未知作用（似乎是图片文件的本地保存路径
     * @param fileSize 文件大小
     * @param fileMD5  文件MD5
     */
    public Picture(String path, String url, int fileSize, String fileMD5) {
        this.path = path;
        this.url = url;
        this.fileSize = fileSize;
        this.fileMD5 = fileMD5;
    }

    /**
     * 这个构造方法一般用于大表情
     *
     * @param forwordBuf   缩略图的Base64编码值
     * @param forwordField 未知作用
     */
    public Picture(String forwordBuf, String forwordField) {
        this.forwordBuf = forwordBuf;
        this.forwordField = forwordField;
    }

    /**
     * 这个构造方法一般用于群的图片消息
     *
     * @param url          原图URL
     * @param fileID       图片ID
     * @param fileSize     图片大小
     * @param fileMD5      图片的MD5
     * @param forwordBuf   缩略图的Base64编码值
     * @param forwordField 未知作用
     */
    public Picture(String url, long fileID, int fileSize, String fileMD5, String forwordBuf, String forwordField) {
        this.url = url;
        this.fileID = fileID;
        this.fileSize = fileSize;
        this.fileMD5 = fileMD5;
        this.forwordBuf = forwordBuf;
        this.forwordField = forwordField;
    }

    public String getPath() {
        return path;
    }

    public String getUrl() {
        return url;
    }

    public long getFileID() {
        return fileID;
    }

    public int getFileSize() {
        return fileSize;
    }

    public String getFileMD5() {
        return fileMD5;
    }

    public String getForwordBuf() {
        return forwordBuf;
    }

    public String getForwordField() {
        return forwordField;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "fileID=" + fileID +
                ", fileSize=" + fileSize +
                ", fileMD5='" + fileMD5 + '\'' +
                ", forwordBuf='" + forwordBuf + '\'' +
                ", forwordField='" + forwordField + '\'' +
                ", url='" + url + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
