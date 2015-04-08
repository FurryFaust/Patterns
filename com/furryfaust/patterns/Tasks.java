package com.furryfaust.patterns;

import com.badlogic.gdx.utils.Timer;

public class Tasks {

    public static class shiftTask extends Timer.Task {

        public GameManager manager;
        public int number, direction, length;
        float completion, speed;

        public shiftTask(GameManager manager, int number, int direction, int length, float speed) {
            this.manager = manager;
            this.number = number;
            this.direction = direction;
            this.length = length;
            this.speed = speed;
            completion = 0.0F;
        }

        @Override
        public void run() {
            if (!isComplete()) {
                completion += (speed / (float) length);
            } else {
                manager.shiftTile(direction);
                cancel();
            }
        }

        public boolean isComplete() {
            return completion >= 1.0F;
        }

        public int getOffset() {
            return (int) (completion * (float) length);
        }

    }
}
