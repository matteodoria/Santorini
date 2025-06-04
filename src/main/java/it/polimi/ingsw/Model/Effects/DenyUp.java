package it.polimi.ingsw.Model.Effects;


public class DenyUp extends EffectOnMovement {

    public DenyUp() {
        this.name = "DenyUp";
    }

    @Override
    public void activateEffect() {
    int curr_row, curr_col, row, col;
    String s;
    String array[]= new String [2];
    curr_row=map.getLookingAtRow();
    curr_col=map.getLookingAtCol();
    map.deactivateNoUp();
      do{
          if(map.getNoUpConstraint())
              handler.sendMessage("GP,3.1");
          else
              handler.sendMessage("GP,3.0");
          s=handler.receiveMessage();
          array=s.split(",");
          row=Integer.parseInt(array[0]);
          col=Integer.parseInt(array[1]);
      }while(!map.move(row, col));
      if(((map.getMap()[row][col].getLevel())-(map.getMap()[curr_row][curr_col].getLevel()))==1){
          map.activateNoUp();
      }else{
          return;
      }
    }

    @Override
    public boolean checkMove() {
        return map.canMoveBasic();
    }

}
