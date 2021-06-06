public class MeetingRoomData {
    private static int roomId;
    private static String[] partipantsList;
    private String creator;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        MeetingRoomData.roomId = roomId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String[] getPartipantsList() {
        return partipantsList;
    }

    public void setPartipantsList(String[] partipantsList) {
        MeetingRoomData.partipantsList = partipantsList;
    }
}
