document.addEventListener("DOMContentLoaded", () => {
    const BLACK_CELLS_COLOR = "white";
    const elements = {
        gridElement: document.getElementById("grid"),
        gridMatrixData: document.getElementById("gridMatrixData"),
        csrfToken: document.getElementById("csrfToken"),
        form: document.getElementById("grid-form"),
        allCells: document.querySelectorAll(".grid-cell"),
    };

    let selectedCell = null;
    let gridMatrix = [];

    function initializeGridMatrix() {


        elements.allCells.forEach(cell => {
            const row = parseInt(cell.getAttribute('data-row'), 10);
            const col = parseInt(cell.getAttribute('data-col'), 10);
            const value = cell.ariaValueText

            if (!gridMatrix[row]) {
                gridMatrix[row] = [];
            }

            gridMatrix[row][col] = value;
        });

    }

    initializeGridMatrix();

    function handleCellClick(cell) {
        if (selectedCell) selectedCell.classList.remove("selected");

        if (cell.style.backgroundColor === BLACK_CELLS_COLOR) {
            selectedCell = null;
            return;
        }

        if (selectedCell === cell) {
            selectedCell = null;
            return;
        }
        selectedCell = cell;
        cell.classList.add("selected");

    }

    elements.allCells.forEach(cell => cell.addEventListener("click", () => handleCellClick(cell)));


    function initializeEvents() {
        const {form} = elements;
        form.addEventListener("submit", (event) => {
            elements.gridMatrixData.value = JSON.stringify(gridMatrix);
            if (!form.checkValidity()) {
                form.reportValidity();
                return;
            }
            form.submit();
        });

        document.addEventListener("keydown", (event) => {
            if (!selectedCell) return;

            const row = parseInt(selectedCell.dataset.row, 10);
            const col = parseInt(selectedCell.dataset.col, 10);

            if (/^[a-zA-Z]$/.test(event.key)) {
                const value = event.key.toUpperCase();
                selectedCell.textContent = value;
                selectedCell.ariaValueText = value;
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
                    selectedCell.ariaValueText = " ";
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
                elements.allCells.forEach(cell => cell.classList.remove("selected"));
            }
        });
    }

    initializeEvents();
});