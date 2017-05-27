package pt.iul.ista.poo.sokoban;

public class Score {

    private int place;
    private int steps;
    private double time;
    private  String name;

    public Score(int place, int steps, double time, String name) {
        this.place = place;
        this.steps = steps;
        this.time = time;
        this.name = name;
    }

    public int getPlace() {
        return place;
    }

    public int getSteps() {
        return steps;
    }

    public String getName() {
        return name;
    }

    public double getTime() {
        return time;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Score score = (Score) o;

        if (place != score.place) return false;
        if (steps != score.steps) return false;
        if (Double.compare(score.time, time) != 0) return false;
        return name.equals(score.name);
    }

}