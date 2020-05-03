package cn.hamster3.bot.data;

/**
 * 消息中所携带的图片或表情。
 * */
public class Picture {
    /**图片ID*/
    public long fileID;
    /**图片大小*/
    public int fileSize;
    /**图片的MD5值*/
    public String fileMD5;
    /**缩略图的Base64编码值*/
    public String forwordBuf;
    /**未知作用*/
    public String forwordField;
    /**原图URL*/
    public String url;
    /**未知作用，只在好友发送的图片中出现*/
    public String path;
}
