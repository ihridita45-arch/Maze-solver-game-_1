# 🧩 Maze Solver Game

A simple game where you can draw mazes and watch an algorithm solve them automatically!

---

## 🎮 What It Does

- Draw your own maze on a grid
- Place a **Start** and **End** point
- Hit **Solve** and watch the path get found in real time
- Supports multiple solving algorithms

---

## 🚀 Getting Started

### 1. Clone the repo
```bash
git clone https://github.com/your-username/maze-solver.git
cd maze-solver
```

### 2. Install dependencies
```bash
npm install
```

### 3. Run the game
```bash
npm start
```

Then open your browser at `http://localhost:3000`

---

## 🕹️ How to Play

| Action | What to do |
|---|---|
| Draw a wall | Click a cell on the grid |
| Set Start | Press `S`, then click a cell |
| Set End | Press `E`, then click a cell |
| Solve | Click the **Solve** button |
| Reset | Click the **Reset** button |

---

## 🧠 Algorithms Included

- **BFS** (Breadth-First Search) — finds the shortest path
- **DFS** (Depth-First Search) — explores deep paths first
- **A\*** — fastest and smartest pathfinder

You can switch between them in the dropdown menu.

---

## 📁 Project Structure

```
maze-solver/
├── index.html       # Main page
├── style.css        # Styling
├── app.js           # Game logic
├── solver.js        # Pathfinding algorithms
└── README.md        # You are here
```

---

## 🛠️ Built With

- HTML, CSS, JavaScript (no frameworks needed!)
- Canvas API for drawing the grid

---

## 📸 Screenshot

```
[ S ][ ][ # ][ ][ ]
[ ][ # ][ # ][ ][ ]
[ ][ ][ ][ # ][ E ]
```
*(S = Start, E = End, # = Wall)*

---

## 📄 License

MIT — free to use and modify.
