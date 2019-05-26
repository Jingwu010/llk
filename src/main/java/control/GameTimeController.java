package control;

import org.json.JSONObject;

/**
 * Created by Jingwu Xu on 2019-05-05
 */
public class GameTimeController{
  private final LLKGame game;

  final static double time_size = 180;
  final static int match_time_bonus = 3;


  public GameTimeController(LLKGame game) {
    this.game = game;
  }

  public void updateTimeBonus() {
    JSONObject jsonObj = new JSONObject();
    jsonObj.put("IDENTIFIER", MessageIdentifier.TIMER);
    jsonObj.put("DATA", match_time_bonus);

    game.mbus.setMessageType(MessageType.POST);
    game.mbus.sendMessage(jsonObj.toString());
  }

  public void resetTime() {
    JSONObject jsonObj = new JSONObject();
    jsonObj.put("IDENTIFIER", MessageIdentifier.TIMER);
    jsonObj.put("DATA", time_size);

    game.mbus.setMessageType(MessageType.PUT);
    game.mbus.sendMessage(jsonObj.toString());
  }
}
