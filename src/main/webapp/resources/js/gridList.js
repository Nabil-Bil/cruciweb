const dropdown = document.querySelector('.dropdown');
const filterButton = document.querySelector('.filter-button');
filterButton.addEventListener('click', () => {
    dropdown.classList.toggle('hidden');

    filterButton.classList.toggle('active');
});
