import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainGUI {

    private static EventManager manager = new EventManager();
    private static JTable table;
    private static DefaultTableModel tableModel;

    // Цвета
    private static final Color BG_COLOR = new Color(225, 240, 250);
    private static final Color PANEL_COLOR = new Color(210, 230, 245);
    private static final Color BTN_COLOR = new Color(147, 197, 228);
    private static final Color BTN_HOVER = new Color(100, 160, 200);
    private static final Color TEXT_COLOR = new Color(40, 70, 100);
    private static final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 20);

    public static void main(String[] args) {
        JFrame frame = new JFrame("Event Planner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 550);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(BG_COLOR);
        frame.setLayout(new BorderLayout(10, 10));

        // Заголовок
        JLabel title = new JLabel("🗓 Event Planner", SwingConstants.CENTER);
        title.setFont(TITLE_FONT);
        title.setForeground(TEXT_COLOR);
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        frame.add(title, BorderLayout.NORTH);

        // Таблица
        String[] columns = {"ID", "Type", "Name", "Date", "Location", "Participants"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        table.setFont(MAIN_FONT);
        table.setRowHeight(28);
        table.setBackground(new Color(240, 248, 255));
        table.setForeground(TEXT_COLOR);
        table.setSelectionBackground(BTN_HOVER);
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(180, 210, 230));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(BTN_COLOR);
        table.getTableHeader().setForeground(TEXT_COLOR);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));
        scrollPane.getViewport().setBackground(new Color(240, 248, 255));
        frame.add(scrollPane, BorderLayout.CENTER);

        // Боковая панель с кнопками
        JPanel sidePanel = new JPanel(new GridLayout(10, 1, 0, 8));
        sidePanel.setBackground(PANEL_COLOR);
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sidePanel.setPreferredSize(new Dimension(170, 0));

        String[] btnLabels = {
                "➕ Add Event",
                "🔄 Refresh",
                "✏️ Edit Event",
                "🗑 Delete Event",
                "👤 Add Participant",
                "👥 Show Participants",
                "🔍 Search",
                "📤 Export CSV",
                "📥 Import CSV",
                "🚪 Exit"
        };

        for (String label : btnLabels) {
            JButton btn = createButton(label);
            sidePanel.add(btn);

            btn.addActionListener(e -> {
                switch (label) {
                    case "➕ Add Event" -> { showAddEventDialog(frame); refreshTable(); }
                    case "🔄 Refresh" -> refreshTable();
                    case "✏️ Edit Event" -> { showEditDialog(frame); refreshTable(); }
                    case "🗑 Delete Event" -> { showDeleteDialog(frame); refreshTable(); }
                    case "👤 Add Participant" -> showAddParticipantDialog(frame);
                    case "👥 Show Participants" -> showParticipantsDialog(frame);
                    case "🔍 Search" -> showSearchDialog(frame);
                    case "📤 Export CSV" -> { manager.exportToCSV(); showSuccess(frame, "Exported to export.csv!"); }
                    case "📥 Import CSV" -> { manager.importFromCSV(); refreshTable(); showSuccess(frame, "Data imported!"); }
                    case "🚪 Exit" -> System.exit(0);
                }
            });
        }

        frame.add(sidePanel, BorderLayout.EAST);

        // Анимация открытия
        frame.setVisible(true);
        refreshTable();
    }

    // КНОПКА С HOVER

    private static JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setBackground(BTN_COLOR);
        btn.setForeground(TEXT_COLOR);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(BTN_HOVER); btn.setForeground(Color.WHITE); }
            public void mouseExited(MouseEvent e) { btn.setBackground(BTN_COLOR); btn.setForeground(TEXT_COLOR); }
        });
        return btn;
    }

    // ОБНОВИТЬ ТАБЛИЦУ

    private static void refreshTable() {
        tableModel.setRowCount(0);
        String raw = manager.getAllEventsAsString();
        if (raw.isEmpty()) return;

        for (Event e : manager. getEventsList()) {
            String type = e.getClass().getSimpleName();
            tableModel.addRow(new Object[]{
                    e.getId(), type, e.getName(),
                    e.getDate(), e.getLocation(),
                    e.getParticipants().size()
            });
        }
    }

    // ДИАЛОГИ

    private static void showAddEventDialog(JFrame frame) {
        String[] types = {"Concert", "Conference", "Birthday"};
        String type = (String) JOptionPane.showInputDialog(frame, "Event type:",
                "Add Event", JOptionPane.QUESTION_MESSAGE, null, types, types[0]);
        if (type == null) return;

        String id = manager.generateId();
        String name = JOptionPane.showInputDialog(frame, "Name:");
        if (name == null || name.isEmpty()) { showError(frame, "Name cannot be empty!"); return; }
        String date = JOptionPane.showInputDialog(frame, "Date (dd.mm.yyyy):");
        if (!isValidDate(date)) { showError(frame, "Invalid date format!"); return; }
        String location = JOptionPane.showInputDialog(frame, "Location:");
        if (location == null || location.isEmpty()) { showError(frame, "Location cannot be empty!"); return; }

        switch (type) {
            case "Concert" -> {
                String artist = JOptionPane.showInputDialog(frame, "Artist:");
                if (artist == null || artist.isEmpty()) return;
                manager.addEvent(new Concert(id, name, date, location, artist));
            }
            case "Conference" -> {
                String topic = JOptionPane.showInputDialog(frame, "Topic:");
                if (topic == null || topic.isEmpty()) return;
                manager.addEvent(new Conference(id, name, date, location, topic));
            }
            case "Birthday" -> {
                String person = JOptionPane.showInputDialog(frame, "Birthday person:");
                if (person == null || person.isEmpty()) return;
                manager.addEvent(new Birthday(id, name, date, location, person));
            }
        }
        showSuccess(frame, "Event added! ID: " + id);
    }

    private static void showEditDialog(JFrame frame) {
        String id = JOptionPane.showInputDialog(frame, "Event ID:");
        if (id == null || id.isEmpty()) return;
        String name = JOptionPane.showInputDialog(frame, "New name:");
        if (name == null || name.isEmpty()) return;
        String date = JOptionPane.showInputDialog(frame, "New date (dd.mm.yyyy):");
        if (!isValidDate(date)) { showError(frame, "Invalid date format!"); return; }
        String location = JOptionPane.showInputDialog(frame, "New location:");
        if (location == null || location.isEmpty()) return;
        manager.updateEvent(id, name, date, location);
        showSuccess(frame, "Event updated!");
    }

    private static void showDeleteDialog(JFrame frame) {
        String id = JOptionPane.showInputDialog(frame, "Event ID:");
        if (id == null || id.isEmpty()) return;
        manager.deleteEvent(id);
        showSuccess(frame, "Event deleted!");
    }

    private static void showAddParticipantDialog(JFrame frame) {
        String id = JOptionPane.showInputDialog(frame, "Event ID:");
        if (id == null || id.isEmpty()) return;
        String name = JOptionPane.showInputDialog(frame, "Participant name:");
        if (name == null || name.isEmpty()) return;
        String email = JOptionPane.showInputDialog(frame, "Participant email:");
        if (!Participant.isValidEmail(email)) { showError(frame, "Invalid email format!"); return; }
        manager.addParticipant(id, new Participant(name, email));
        showSuccess(frame, "Participant added!");
    }

    private static void showParticipantsDialog(JFrame frame) {
        String id = JOptionPane.showInputDialog(frame, "Event ID:");
        if (id == null || id.isEmpty()) return;
        String result = manager.getParticipantsAsString(id);
        JOptionPane.showMessageDialog(frame, result.isEmpty() ? "No participants yet." : result);
    }

    private static void showSearchDialog(JFrame frame) {
        String keyword = JOptionPane.showInputDialog(frame, "Enter keyword:");
        if (keyword == null || keyword.isEmpty()) return;
        String result = manager.searchByNameAsString(keyword);
        JOptionPane.showMessageDialog(frame, result.isEmpty() ? "No events matched." : result);
    }

    // ВСПОМОГАТЕЛЬНЫЕ

    private static void showSuccess(JFrame frame, String msg) {
        JOptionPane.showMessageDialog(frame, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void showError(JFrame frame, String msg) {
        JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private static boolean isValidDate(String date) {
        if (date == null || date.isEmpty()) return false;
        return date.matches("\\d{2}\\.\\d{2}\\.\\d{4}");
    }
}