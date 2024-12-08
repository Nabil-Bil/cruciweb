document.addEventListener("DOMContentLoaded", () => {
    const gridElement = document.getElementById("grid");
    const columnIndicesElement = document.getElementById("column-indices");
    const rowIndicesElement = document.getElementById("row-indices");
    const gridNameInput = document.getElementById("grid-name");
    const gridDifficultyInput = document.getElementById("grid-difficulty");

    const widthInput = document.getElementById("grid-width");
    const heightInput = document.getElementById("grid-height");
    const resetButton = document.getElementById("reset-button");
    const saveButton = document.getElementById("save-button");
    let selectedCell = null;
    let gridMatrix = [];

    function generateGrid(width, height) {
        gridElement.innerHTML = "";
        columnIndicesElement.innerHTML = "";
        rowIndicesElement.innerHTML = "";
        gridMatrix = Array.from({length: height}, () => Array(width).fill(""));

        for (let i = 0; i < width; i++) {
            const letter = String.fromCharCode(65 + i);
            const columnIndex = document.createElement("span");
            columnIndex.textContent = letter;
            columnIndicesElement.appendChild(columnIndex);
        }

        gridElement.style.gridTemplateColumns = `repeat(${width}, 30px)`;

        for (let i = 0; i < height; i++) {
            const rowIndex = document.createElement("span");
            rowIndex.textContent = i + 1;
            rowIndicesElement.appendChild(rowIndex);

            for (let j = 0; j < width; j++) {
                const gridCell = document.createElement("div");
                gridCell.classList.add("grid-cell");

                gridCell.addEventListener("click", () => {
                    if (gridCell.style.backgroundColor === "black") {
                        return;
                    }
                    if (selectedCell) {
                        selectedCell.classList.remove("selected");
                    }
                    if (selectedCell === gridCell) {
                        selectedCell = null;
                        return;
                    }

                    selectedCell = gridCell;
                    selectedCell.classList.add("selected");
                });

                gridCell.dataset.row = i;
                gridCell.dataset.col = j;

                gridElement.appendChild(gridCell);
            }
        }
    }

    resetButton.addEventListener("click", () => {
        gridNameInput.value = "";
        widthInput.value = 6;
        heightInput.value = 6;
        gridDifficultyInput.value = "easy";
        generateGrid(6, 6);
    });

    saveButton.addEventListener("click", () => {
        if (gridNameInput.checkValidity() === false) {
            gridNameInput.reportValidity();
            return;
        }

        const gridData = {
            name: gridNameInput.value,
            difficulty: gridDifficultyInput.value,
            width: parseInt(widthInput.value, 10),
            height: parseInt(heightInput.value, 10),
            grid: gridMatrix,
        };
        console.log(gridData);
    });

    document.addEventListener("keydown", (event) => {
        if (selectedCell) {
            const row = parseInt(selectedCell.dataset.row, 10);
            const col = parseInt(selectedCell.dataset.col, 10);

            if (/^[a-zA-Z]$/.test(event.key)) {
                const value = event.key.toUpperCase();
                selectedCell.textContent = value;
                gridMatrix[row][col] = value;
            } else if (event.key === "Backspace") {
                selectedCell.textContent = "";
                gridMatrix[row][col] = "";
            }
        }
    });

    gridElement.addEventListener("contextmenu", (event) => {
        event.preventDefault();
        if (event.target.classList.contains("grid-cell")) {
            const row = parseInt(event.target.dataset.row, 10);
            const col = parseInt(event.target.dataset.col, 10);
            if (selectedCell === event.target) {
                selectedCell.classList.remove("selected");
                selectedCell = null;
            }
            if (gridMatrix[row][col] === "*") {
                gridMatrix[row][col] = "";
                event.target.style.backgroundColor = "";
                event.target.textContent = "";
            } else {
                gridMatrix[row][col] = "*";
                event.target.style.backgroundColor = "black";
                event.target.textContent = "";
            }
        }
    });

    widthInput.addEventListener("input", () => {
        if (!/^\d*$/.test(widthInput.value)) {
            widthInput.value = widthInput.value.replace(/\D/g, "");
        }
        if (widthInput.value === "") {
            widthInput.value = 6;
        }
        const width = Math.min(Math.max(parseInt(widthInput.value, 10), 6), 26);
        widthInput.value = width;
        generateGrid(width, parseInt(heightInput.value, 10));
    });

    heightInput.addEventListener("input", () => {
        if (!/^\d*$/.test(heightInput.value)) {
            heightInput.value = heightInput.value.replace(/\D/g, "");
        }
        if (heightInput.value === "") {
            heightInput.value = 6;
        }
        const height = Math.min(Math.max(parseInt(heightInput.value, 10), 6), 26);
        heightInput.value = height;
        generateGrid(parseInt(widthInput.value, 10), height);
    });

    generateGrid(parseInt(widthInput.value, 10), parseInt(heightInput.value, 10));
});
