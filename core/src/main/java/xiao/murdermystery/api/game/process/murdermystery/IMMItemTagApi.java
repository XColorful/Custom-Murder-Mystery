package xiao.murdermystery.api.game.process.murdermystery;

public interface IMMItemTagApi extends IMMItemTagReadApi, IMMItemTagWriteApi {

    void setSurvivorTag(String survivorTagName);
    void setMurderTag(String murderTagName);
}
