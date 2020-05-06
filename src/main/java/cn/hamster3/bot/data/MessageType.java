package cn.hamster3.bot.data;

/**
 * 消息类型。[G]:只支持群消息，[F]:只支持好友消息，[FG]:支持群消息和好友消息，未注明表示暂未支持解析
 */
public enum MessageType {
    /**
     * 纯文本消息[FG]
     */
    TextMsg,
    /**
     * @ 群成员的消息[G]
     */
    AtMsg,
    /**
     * 图片消息[FG]
     */
    PicMsg,
    /**
     * 大表情消息[FG]
     */
    BigFaceMsg,
    /**
     * 红包消息(只在红包发出的时候会收到此消息)
     */
    RedBagMsg,
    /**
     * 语音消息[G](获取不到好友发送的语音消息)
     */
    VoiceMsg,
    /**
     * Json格式的复杂消息
     */
    JsonMsg,
    /**
     * Xml格式的复杂消息
     */
    XmlMsg,
    /**
     * 群文件消息
     */
    GroupFileMsg,
    /**
     * 回复消息
     */
    ReplayMsg,
    /**
     * 其他类型的消息
     */
    UnknownMsg
}
