package com.furryfaust.apps.patterns;

import com.badlogic.gdx.utils.Timer;

public class Tasks {

    public static class countTask extends Timer.Task {

        Manager manager;

        public countTask(Manager manager) {
            this.manager = manager;
        }

        @Override
        public void run() {
            if (!manager.checkBoard()) {
                manager.timePlayed++;
            } else {
                cancel();
            }
        }
    }

    public static class shiftTask extends Timer.Task {

        public Manager manager;
        public int number, direction, length;
        float completion, speed;

        public shiftTask(Manager manager, int number, int direction, int length, float speed) {
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
                if (!manager.checkBoard()) {
                    manager.shiftTile(direction, false);
                }
                cancel();
            }
        }

        public boolean isComplete() {
            return completion >= 1.0F;
        }

        public int getOffset() {
            return completion >= 1.0F ? length : (int) (completion * (float) length);
        }

    }
}
