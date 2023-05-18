const counterLike = document.getElementById('like');
const counterDislike = document.getElementById('dislike');

const likes = document.getElementById('likeCount');
const dislikes = document.getElementById('dislikeCount');

let counterLikeAtt= 0;
let counterDislikeAtt = 0;
let attLike = 0;
let attDislike = 0;

counterDislike.addEventListener("click",() =>{
        if (attDislike === 0) {
            counterDislikeAtt = counterDislikeAtt + 1;
            attDislike++;
            console.log("dislike = " + counterDislikeAtt)
            dislikes.textContent = counterDislikeAtt;
            likes.textContent  = 0
        }
        else{
            counterDislikeAtt = counterDislikeAtt - 1;
        console.log("dislike = " + counterDislikeAtt)
        attDislike = 0;
        dislikes.textContent = counterDislikeAtt;

        }
})
counterLike.addEventListener("click", () =>{
        if (attLike === 0) {
            counterLikeAtt = counterLikeAtt + 1;
            attLike++;
            console.log("like = " + counterLikeAtt)
            likes.textContent = counterLikeAtt;
            dislikes.textContent = 0
        }
        else {
            counterLikeAtt = counterLikeAtt - 1;
            console.log("like = " + counterLikeAtt)
            attLike = 0;
            likes.textContent = counterLikeAtt;
        }
})

const saveButton = document.getElementById("saveButtonDisabled");

saveButton.addEventListener("click",() =>{
    alert("register/login to save movie");
})
// const savingButton = document.getElementById("savedFilm");
// let clicked = false;
//
// savingButton.addEventListener("click",() =>{
//     if(!clicked){
//         savingButton.innerText = "Added";
//         clicked = true;
//     } else {
//         savingButton.innerText = "Click to add";
//         clicked = false;
//     }
// });

function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}

// Close the dropdown if the user clicks outside of it
window.onclick = function(e) {
    if (!e.target.matches('.dropbtn')) {
        var myDropdown = document.getElementById("myDropdown");
        if (myDropdown.classList.contains('show')) {
            myDropdown.classList.remove('show');
        }
    }
}
const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))























