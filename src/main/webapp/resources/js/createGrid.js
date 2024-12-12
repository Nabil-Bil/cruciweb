document.addEventListener("DOMContentLoaded", () => {
    const MIN_GRID_SIZE = 6;
    const MAX_GRID_SIZE = 26;
    const CELL_SIZE = 30;
    const BLACK_CELLS_COLOR = "white";
    const elements = {
        gridElement: document.getElementById("grid"),
        columnIndicesElement: document.getElementById("column-indices"),
        rowIndicesElement: document.getElementById("row-indices"),
        gridNameInput: document.getElementById("grid-name"),
        gridDifficultyInput: document.getElementById("grid-difficulty"),
        gridMatrixData: document.getElementById("gridMatrixData"),
        widthInput: document.getElementById("grid-width"),
        heightInput: document.getElementById("grid-height"),
        resetButton: document.getElementById("reset-button"),
        csrfToken: document.getElementById("csrfToken"),
        form: document.getElementById("grid-form"),
        horizontalClues: document.getElementById("horizontal-clues"),
        verticalClues: document.getElementById("vertical-clues")
    };

    let selectedCell = null;
    let gridMatrix = [];

    function generateGrid(width, height) {
        const {gridElement, columnIndicesElement, rowIndicesElement, verticalClues, horizontalClues} = elements;

        gridElement.innerHTML = "";
        columnIndicesElement.innerHTML = "";
        rowIndicesElement.innerHTML = "";
        verticalClues.innerHTML = "";
        horizontalClues.innerHTML = "";
        gridMatrix = Array.from({length: height}, () => Array(width).fill(" "));

        for (let i = 0; i < width; i++) {
            const columnIndex = document.createElement("span");
            let letter = String.fromCharCode(65 + i);
            columnIndex.textContent = letter
            columnIndicesElement.appendChild(columnIndex);
            const verticalClue = document.createElement("span");
            verticalClue.classList.add("clue");
            verticalClue.textContent = letter;
            const verticalClueInput = document.createElement("input");
            verticalClueInput.type = "text";
            verticalClueInput.name = `clue-column-${i}`;
            verticalClue.appendChild(verticalClueInput);
            verticalClues.appendChild(verticalClue);
        }

        gridElement.style.gridTemplateColumns = `repeat(${width}, ${CELL_SIZE}px)`;

        for (let i = 0; i < height; i++) {
            const rowIndex = document.createElement("span");
            rowIndex.textContent = i + 1;
            rowIndicesElement.appendChild(rowIndex);
            const horizontalClue = document.createElement("span");
            horizontalClue.classList.add("clue");
            horizontalClue.textContent = i + 1;
            const horizontalClueInput = document.createElement("input");
            horizontalClueInput.type = "text";
            horizontalClueInput.name = `clue-row-${i}`;
            horizontalClue.appendChild(horizontalClueInput);
            horizontalClues.appendChild(horizontalClue);

            for (let j = 0; j < width; j++) {
                const gridCell = createGridCell(i, j);
                gridElement.appendChild(gridCell);
            }
        }
    }

    function createGridCell(row, col) {
        const gridCell = document.createElement("div");
        gridCell.classList.add("grid-cell");
        gridCell.dataset.row = row;
        gridCell.dataset.col = col;

        gridCell.addEventListener("click", () => handleCellClick(gridCell));
        gridCell.addEventListener("contextmenu", (event) => handleCellRightClick(event, gridCell));

        return gridCell;
    }

    function handleCellClick(cell) {
        if (cell.style.backgroundColor === BLACK_CELLS_COLOR) {
            selectedCell = null;
            return;
        }

        if (selectedCell) selectedCell.classList.remove("selected");

        if (selectedCell === cell) {
            selectedCell = null;
        } else {
            selectedCell = cell;
            cell.classList.add("selected");
        }
    }

    function handleCellRightClick(event, cell) {
        event.preventDefault();
        if (selectedCell === cell) {
            selectedCell.classList.remove("selected")
            selectedCell = null;
        }
        const row = parseInt(cell.dataset.row, 10);
        const col = parseInt(cell.dataset.col, 10);

        if (gridMatrix[row][col] === "*") {
            gridMatrix[row][col] = " ";
            cell.style.backgroundColor = "";
            cell.textContent = "";
        } else {
            gridMatrix[row][col] = "*";
            cell.style.backgroundColor = BLACK_CELLS_COLOR;
            cell.textContent = "";
        }
    }

    function resetGrid() {
        const {gridNameInput, widthInput, heightInput, gridDifficultyInput} = elements;
        gridNameInput.value = "";
        widthInput.value = MIN_GRID_SIZE;
        heightInput.value = MIN_GRID_SIZE;
        gridDifficultyInput.value = "easy";
        generateGrid(MIN_GRID_SIZE, MIN_GRID_SIZE);
    }

    function updateGridSize() {
        const {widthInput, heightInput} = elements;

        const width = clamp(parseInt(widthInput.value, 10) || MIN_GRID_SIZE, MIN_GRID_SIZE, MAX_GRID_SIZE);
        const height = clamp(parseInt(heightInput.value, 10) || MIN_GRID_SIZE, MIN_GRID_SIZE, MAX_GRID_SIZE);

        widthInput.value = width;
        heightInput.value = height;

        generateGrid(width, height);
    }

    function clamp(value, min, max) {
        return Math.min(Math.max(value, min), max);
    }

    function initializeEvents() {
        const {resetButton, widthInput, heightInput, form} = elements;
        form.addEventListener("submit", (event) => {
            elements.gridMatrixData.value = JSON.stringify(gridMatrix);
            if (!form.checkValidity()) {
                form.reportValidity();
                return;
            }
            form.submit();
        });

        resetButton.addEventListener("click", resetGrid);

        widthInput.addEventListener("input", updateGridSize);
        heightInput.addEventListener("input", updateGridSize);

        document.addEventListener("keydown", (event) => {
            if (!selectedCell) return;

            const row = parseInt(selectedCell.dataset.row, 10);
            const col = parseInt(selectedCell.dataset.col, 10);

            if (/^[a-zA-Z]$/.test(event.key)) {
                const value = event.key.toUpperCase();
                selectedCell.textContent = value;
                gridMatrix[row][col] = value;

                let nextRow = row;
                let nextCol = col + 1;

                if (nextCol >= gridMatrix[0].length) {
                    nextCol = 0;
                    nextRow = row + 1;
                }

                if (nextRow < gridMatrix.length) {
                    let nextCell = document.querySelector(`[data-row='${nextRow}'][data-col='${nextCol}']`);

                    while (nextCell && gridMatrix[nextRow][nextCol] === "*") {
                        nextCol += 1;

                        if (nextCol >= gridMatrix[0].length) {
                            nextCol = 0;
                            nextRow = row + 1;
                        }

                        if (nextRow >= gridMatrix.length) {
                            return;
                        }

                        nextCell = document.querySelector(`[data-row='${nextRow}'][data-col='${nextCol}']`);
                    }

                    if (nextCell) {
                        selectedCell.classList.remove("selected");
                        nextCell.classList.add("selected");
                        selectedCell = nextCell;
                    }
                }
            } else if (event.key === "Backspace") {
                if (selectedCell.textContent !== "") {
                    selectedCell.textContent = "";
                    gridMatrix[row][col] = " ";
                } else {
                    let prevRow = row;
                    let prevCol = col - 1;

                    if (prevCol < 0) {
                        prevCol = gridMatrix[0].length - 1;
                        prevRow = row - 1;
                    }

                    if (prevRow >= 0) {
                        let prevCell = document.querySelector(`[data-row='${prevRow}'][data-col='${prevCol}']`);

                        while (prevCell && gridMatrix[prevRow][prevCol] === "*") {
                            prevCol -= 1;

                            if (prevCol < 0) {
                                prevCol = gridMatrix[0].length - 1;
                                prevRow -= 1;
                            }

                            if (prevRow < 0) {
                                return;
                            }

                            prevCell = document.querySelector(`[data-row='${prevRow}'][data-col='${prevCol}']`);
                        }

                        if (prevCell) {
                            selectedCell.classList.remove("selected");
                            prevCell.classList.add("selected");
                            selectedCell = prevCell;
                        }
                    }
                }
            }
        });

        document.addEventListener("click", (event) => {
            if (!elements.gridElement.contains(event.target)) {
                selectedCell = null;
                const allCells = document.querySelectorAll(".grid-cell");
                allCells.forEach(cell => cell.classList.remove("selected"));
            }
        });
    }

    initializeEvents();
    resetGrid();
});