    package com.example.mynote1;



    import java.io.Serializable;


    public class Note implements Serializable{
        private int id;


        private String title;


        private String dateTime;


        private String subtitle;


        private String noteText;


        private String color;




        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getNoteText() {
            return noteText;
        }

        public void setNoteText(String noteText) {
            this.noteText = noteText;
        }



        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }


        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Note{");
            sb.append("id=").append(id);
            sb.append(", title='").append(title).append('\'');
            sb.append(", dateTime='").append(dateTime).append('\'');
            sb.append(", subtitle='").append(subtitle).append('\'');
            sb.append(", noteText='").append(noteText).append('\'');
            sb.append(", color='").append(color).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
