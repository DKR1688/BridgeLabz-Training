package ParcelTracker;

//this is node class to each stage in parcel delivery
class Stage {
    String name;
    Stage next;

    Stage(String name) {
        this.name =name;
        this.next =null;
    }
}

