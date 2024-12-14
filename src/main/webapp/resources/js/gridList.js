const dropdown = document.querySelector('.dropdown');
const sortButton = document.querySelector('.sort-button');
sortButton.addEventListener('click', () => {
    dropdown.classList.toggle('hidden');
    sortButton.classList.toggle('active');
});
